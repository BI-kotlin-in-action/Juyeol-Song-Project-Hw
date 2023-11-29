package com.example.lottoweb.repository

import com.example.lottoweb.domain.model.LottoRecord
import com.example.lottoweb.domain.model.LottoRecordId
import com.example.lottoweb.dto.LottoRecordResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

/**
 * @author Unagi_zoso
 * @since 2023-11-23
 */
interface LottoRecordRepository : JpaRepository<LottoRecord, LottoRecordId> {
    // user가 round 회차 동안 구매한 로또의 최고 순번을 구한다. (이전에 2개 까지 구매했으면 이번엔 3부터 기록하기 위해 구하는 것)
    @Query("SELECT MAX(lr.currentCounterOfThisRound) FROM lotto_record lr WHERE lr.round = :round AND lr.lottoRecordId.userId = :userId")
    fun findMaxLottoCounterByRoundAndUserId(@Param("round") round: Int, @Param("userId") userId: Long): Long?

    @Query(
        """
        SELECT new com.example.lottoweb.dto.LottoRecordResponse(lr.round, lr.currentCounterOfThisRound, u.username, l.numbersAsString) 
        FROM lotto_record lr 
        JOIN lotto l ON lr.lottoRecordId.lottoId = l.lottoId
        JOIN user u ON lr.lottoRecordId.userId = u.userId
        WHERE u.userId = :userId
            AND lr.round = :round
        """,
    )
    fun findByUserIdAndRound(userId: Long, round: Int): List<LottoRecordResponse>
}
