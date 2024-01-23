package com.example.lottoweb.service

import com.example.lottoweb.domain.LottoJob
import com.example.lottoweb.domain.LottoNumbers
import com.example.lottoweb.domain.WinningPrize.Converter.getPrize
import com.example.lottoweb.domain.model.ResultRecord
import com.example.lottoweb.domain.model.WinningRecord
import com.example.lottoweb.dto.LottoResultsResponse
import com.example.lottoweb.exception.UsernameNotFoundException
import com.example.lottoweb.repository.ResultRecordRepository
import jakarta.transaction.Transactional
import java.util.SortedSet
import org.springframework.stereotype.Service

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
@Service
class ResultRecordService(
    private val resultRecordRepository: ResultRecordRepository,
    private val userService: UserService,
) {
    @Transactional
    fun processResultRecords(lottoJob: LottoJob, winningRecordThisRound: WinningRecord) {
        val matchedNumberSet = getMatchedNumberSet(lottoJob.numbers, winningRecordThisRound.lottoNumbers)
        val winningPrize = getPrize(matchedNumberSet.size)
        val user = userService.findUserByUsername(lottoJob.username)

        user.depositBalance(winningPrize.prizeAmount)

        resultRecordRepository.save(
            ResultRecord(
                round = winningRecordThisRound.round,
                currentCounterOfThisRound = lottoJob.currentCounter,
                userId = userService.findUserByUsername(lottoJob.username).userId ?: throw UsernameNotFoundException(),
                username = lottoJob.username,
                matchedNumber = matchedNumberSet.joinToString(" "),
                numOfMatched = matchedNumberSet.size,
                prizeRank = winningPrize.rank,
                prizeAmount = winningPrize.prizeAmount,
            ),
        )
    }

    // otherLotto와 당첨 번호를 비교하여 일치하는 번호를 SortedSet으로 반환한다.
    private fun getMatchedNumberSet(winningLotto: LottoNumbers, otherLotto: LottoNumbers): SortedSet<Int> {
        return (winningLotto.getNumbers() intersect otherLotto.getNumbers()).toSortedSet()
    }

    // round 회차의 결과를 가져온다.
    fun getResultRecord(round: Int, username: String): LottoResultsResponse {
        val user = userService.findUserByUsername(username)
        return LottoResultsResponse.from(
            resultRecordRepository.findAllByRoundAndUserId(
                round,
                user.userId
                    ?: throw UsernameNotFoundException(),
            ),
        )
    }
}
