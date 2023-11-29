package com.example.lottoweb.service

import com.example.lottoweb.domain.AutomaticLottoNumberGenerator.Companion.generateAutoLottoNumbers
import com.example.lottoweb.domain.LottoJobMessage
import com.example.lottoweb.domain.LottoJobMessage.Companion.from
import com.example.lottoweb.domain.LottoJobMessageQueuePool
import com.example.lottoweb.domain.LottoNumbers
import com.example.lottoweb.domain.model.Lotto
import com.example.lottoweb.domain.model.User
import com.example.lottoweb.dto.LottoBuyRequest
import com.example.lottoweb.repository.LottoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */

/**
 * 수동 로또 번호는 외부에서 입력받는데 자연스럽게 전략 패턴을 만들 수 있을까요?
 * 로컬환경에서 로또 프로그램 만들 땐 수동 로또 전략 내부에 view를 넣어 번호 생성 시점에 입력을
 * 받을 수 있게 했는데 이 경우엔 입력 시점과 생성 시점이 다르니 전략 패턴이 무척 어색해지는 기분입니다.
*/
@Service
class LottoService(
    private val userService: UserService,
    private val lottoRecordService: LottoRecordService,
    private val queueWorkingCounterService: QueueWorkingCounterService,
    private val lottoRoundControlService: LottoRoundControlService,
    private val lottoRepository: LottoRepository,
) {
    @Transactional(isolation = Isolation.READ_COMMITTED)
    fun processLottoBuyRequest(lottoBuyRequest: LottoBuyRequest, username: String) {
        // 로또 생성 시 10분이 지나더라도 생성 진입 시 잡아둔 round를 사용한다.
        // ex ) 0시 10분에 당첨번호 공개 시 0시 9분 58초에 구매하여
        // 현재 회차를 캡쳐한다면 당첨번호가 공개되었다 해도 구매한 로또는 전부 그 회차의 로또가 됩니다.
        val capturedCurrentRound = lottoRoundControlService.getLottoRound()
        // 이번 회차에 생성 작업하고 있음을 DB에 기록한다.
        queueWorkingCounterService.increaseCounter(capturedCurrentRound)

        val user = userService.findUserByUsername(username)
        payAllCost(user, lottoBuyRequest.numOfManual, lottoBuyRequest.numOfAuto)

        var currentCounterOfThisRound = lottoRecordService.findMaxLottoCounterByRoundAndUserId(capturedCurrentRound, user.userId as Long)

        currentCounterOfThisRound = processLottoBuy(
            lottoBuyRequest.manualLottoNumbers, // 수동 로또 구매
            currentCounterOfThisRound,
            capturedCurrentRound,
            username,
            user.userId,
        )

        val autoLottos = List(lottoBuyRequest.numOfAuto) { generateAutoLottoNumbers().getNumbersAsString() }
        processLottoBuy(
            autoLottos, // 자동 로또 구매
            currentCounterOfThisRound,
            capturedCurrentRound,
            username,
            user.userId,
        )
        // 구매 작업을 마치고 현재 회차 구매 작업이 끝났음을 DB에 기록한다.
        queueWorkingCounterService.decreaseCounter(capturedCurrentRound)
    }

    private fun payAllCost(user: User, numOfManual: Int, numOfAuto: Int) {
        user.withdrawBalance(100L * (numOfManual + numOfAuto))
    }

    // 구매할 로또를 처리하고 구매한 로또의 최고 순번을 반환합니다.
    private fun processLottoBuy(
        lottoNumbers: List<String>,
        currentCounterOfThisRound: Long,
        currentRound: Int,
        username: String,
        userId: Long,
    ): Long {
        var varCurrentCounterOfThisRound = currentCounterOfThisRound
        for (numbers in lottoNumbers) {
            saveMessageAndLotto(userId, ++varCurrentCounterOfThisRound, currentRound, numbers, username)
        }
        return varCurrentCounterOfThisRound
    }

    // LottoJobQueue를 저장하고 Lotto를 저장합니다.
    private fun saveMessageAndLotto(
        userId: Long,
        currentCounterOfThisRound: Long,
        currentRound: Int,
        lottoNumberString: String,
        username: String,
    ) {
        sendLottoBuyMessage(from(currentRound, currentCounterOfThisRound, username, lottoNumberString), currentRound)
        saveLottoAndRecord(lottoNumberString, userId, currentRound, currentCounterOfThisRound)
    }

    // 현재 회차의 LottoJobQueue에 LottoJobMessage를 저장합니다.
    private fun sendLottoBuyMessage(message: LottoJobMessage, currentRound: Int) {
        LottoJobMessageQueuePool.getQueueByRound(currentRound).addLast(message)
    }

    // Lotto와 LottoRecord를 저장합니다.
    private fun saveLottoAndRecord(
        lottoNumberString: String,
        userId: Long,
        currentRound: Int,
        currentCounter: Long,
    ) {
        val lotto = lottoRepository.save(Lotto.from(LottoNumbers.from(lottoNumberString)))
        lottoRecordService.saveLottoRecord(userId, lotto.lottoId as Long, currentRound, currentCounter)
    }
}
