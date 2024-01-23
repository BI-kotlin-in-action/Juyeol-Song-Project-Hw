package com.example.lottoweb.service

import com.example.lottoweb.domain.LottoJob
import com.example.lottoweb.domain.LottoJobQueuePool
import com.example.lottoweb.domain.LottoNumbers
import com.example.lottoweb.domain.RandomLottoNumberGenerator.Companion.generateRandomLottoNumbers
import com.example.lottoweb.domain.model.Lotto
import com.example.lottoweb.domain.model.User
import com.example.lottoweb.dto.BoughtLottoResponse
import com.example.lottoweb.dto.BoughtLottosResponse
import com.example.lottoweb.dto.LottoBuyRequest
import com.example.lottoweb.repository.LottoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
@Service
class LottoBuyService(
    private val userService: UserService,
    private val lottoRecordService: LottoRecordService,
    private val queueWorkingCounterService: QueueWorkingCounterService,
    private val lottoRoundControlService: LottoRoundControlService,
    private val lottoRepository: LottoRepository,
) {
    @Transactional(isolation = Isolation.READ_COMMITTED)
    fun processLottoBuyRequest(lottoBuyRequest: LottoBuyRequest, username: String): BoughtLottosResponse {
        // 로또 생성 시 10분이 지나더라도 생성 진입 시 잡아둔 round를 사용한다.
        // ex ) 0시 10분에 당첨번호 공개 시 0시 9분 58초에 구매하여
        // 현재 회차를 캡쳐한다면 당첨번호가 공개되었다 해도 구매한 로또는 전부 그 회차의 로또가 됩니다.
        val capturedCurrentRound = lottoRoundControlService.getLottoRound()
        queueWorkingCounterService.increaseCounter(capturedCurrentRound)
        // 이번 회차에 생성 작업하고 있음을 DB에 기록한다.

        val user = userService.findUserByUsername(username)
        payAllCost(user, lottoBuyRequest.numOfManual, lottoBuyRequest.numOfAuto)
        val response = makeLottos(capturedCurrentRound, user, lottoBuyRequest, username)
        queueWorkingCounterService.decreaseCounter(capturedCurrentRound)
        return response
    }

    private fun makeLottos(
        round: Int,
        user: User,
        lottoBuyReq: LottoBuyRequest,
        username: String
    ): BoughtLottosResponse {
        val currentCounterOfThisRound =
            lottoRecordService.findMaxLottoCounterByRoundAndUserId(round, user.userId as Long)
        val beginCounter = currentCounterOfThisRound + 1

        val lottoNumbersList = lottoBuyReq.manualLottoNumbers.getNumbersList()+
                List(lottoBuyReq.numOfAuto) { generateRandomLottoNumbers() }

        processLottoBuy(
            lottoNumbersList,
            currentCounterOfThisRound,
            round,
            username,
            user.userId,
        )

        return sendLottoBuyResponse(beginCounter, round, lottoNumbersList)
    }

    private fun sendLottoBuyResponse(beginCounter: Long, round: Int, lottoNumbersList: List<LottoNumbers>): BoughtLottosResponse =
        BoughtLottosResponse.from(
            lottoNumbersList.mapIndexed { index, lottoNumbers ->
                BoughtLottoResponse.from(round, beginCounter + index, lottoNumbers)
            }
        )

    private fun payAllCost(user: User, numOfManual: Int, numOfAuto: Int) {
        user.withdrawBalance(100L * (numOfManual + numOfAuto))
    }

    // 구매할 로또를 처리하고 구매한 로또의 최고 순번을 반환합니다.
    private fun processLottoBuy(
        lottoNumbers: List<LottoNumbers>,
        currentCounterOfThisRound: Long,
        currentRound: Int,
        username: String,
        userId: Long,
    ) {
        var varCurrentCounterOfThisRound = currentCounterOfThisRound
        for (numbers in lottoNumbers) {
            saveMessageAndLotto(userId, ++varCurrentCounterOfThisRound, currentRound, numbers, username)
        }
    }

    // LottoJobQueue를 저장하고 Lotto를 저장합니다.
    private fun saveMessageAndLotto(
        userId: Long,
        currentCounterOfThisRound: Long,
        currentRound: Int,
        lottoNumber: LottoNumbers,
        username: String,
    ) {
        sendLottoJob(LottoJob(currentRound, currentCounterOfThisRound, username, lottoNumber))
        saveLottoAndRecord(lottoNumber, userId, currentRound, currentCounterOfThisRound)
    }

    // 현재 회차의 LottoJobQueue에 LottoJobMessage를 저장합니다.
    private fun sendLottoJob(lottoJob: LottoJob) {
        LottoJobQueuePool.getQueueByRound(lottoJob.round).addLast(lottoJob)
    }

    // Lotto와 LottoRecord를 저장합니다.
    private fun saveLottoAndRecord(
        lottoNumber: LottoNumbers,
        userId: Long,
        currentRound: Int,
        currentCounter: Long,
    ) {
        val lotto = lottoRepository.save(Lotto(lottoNumbers = lottoNumber))
        lottoRecordService.saveLottoRecord(userId, lotto.lottoId as Long, currentRound, currentCounter)
    }
}
