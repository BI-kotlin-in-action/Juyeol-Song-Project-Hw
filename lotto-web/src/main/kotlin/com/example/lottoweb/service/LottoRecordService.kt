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
    @Transactional
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

    fun findMaxLottoCounterByRoundAndUserId(currentRound: Int, userId: Long) =
        lottoRecordRepository.findMaxLottoCounterByRoundAndUserId(currentRound, userId) ?: 0L

    // lazy loading을 사용하지 않기에 조회용 메소드엔 트랜잭션을 걸지 않았습니다. @Transactional(readOnly = true)라도 거는게 좋을까요/
    fun getLottoRecordsByUsernameAndRound(username: String, round: Int): LottoRecordsResponse {
        val userId = userService.findUserIdByUsername(username)
        val lottoRecordResponses = lottoRecordRepository.findByUserIdAndRound(userId, round)
        return LottoRecordsResponse.from(lottoRecordResponses)
    }
}
