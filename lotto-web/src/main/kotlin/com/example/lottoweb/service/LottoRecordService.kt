package com.example.lottoweb.service

import com.example.lottoweb.domain.model.LottoRecord
import com.example.lottoweb.domain.model.LottoRecordId
import com.example.lottoweb.dto.LottoRecordsResponse
import com.example.lottoweb.repository.LottoRecordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LottoRecordService(
    private val lottoRecordRepository: LottoRecordRepository,
    private val userService: UserService,
) {
    fun saveLottoRecord(userId: Long, lottoId: Long, currentRound: Int, currentCounter: Long) {
        lottoRecordRepository.save(
            LottoRecord(
                lottoRecordId = LottoRecordId(
                    userId = userId,
                    lottoId = lottoId,
                ),
                round = currentRound,
                currentCounterOfThisRound = currentCounter,
            ),
        )
    }

    @Transactional
    fun findMaxLottoCounterByRoundAndUserId(currentRound: Int, userId: Long) =
        lottoRecordRepository.findMaxLottoCounterByRoundAndUserId(currentRound, userId) ?: 0L

    @Transactional
    fun getLottoRecordsByUsernameAndRound(username: String, round: Int): LottoRecordsResponse {
        val userId = userService.findUserIdByUsername(username)
        val lottoRecordResponses = lottoRecordRepository.findByUserIdAndRound(userId, round)
        return LottoRecordsResponse.from(lottoRecordResponses)
    }
}
