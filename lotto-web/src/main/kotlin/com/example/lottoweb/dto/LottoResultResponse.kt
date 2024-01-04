package com.example.lottoweb.dto

import com.example.lottoweb.domain.model.ResultRecord

/**
 * @author Unagi_zoso
 * @since 2023-11-25
 */
// 로또 결과를 반환 받는 DTO
// 실제론 다수 개의 결과를 반환 받기에 일급컬렉션의 LottoResultsResponse를 실질적으로 사용한다.
/**
 * @see com.example.lottoweb.dto.LottoResultsResponse
 */
data class LottoResultResponse(
    val round: Int,
    val currentCounterOfThisRound: Long,
    val username: String,
    val matchedNumbers: String,
    val numOfMatched: Int,
    val prizeRank: String,
    val prizeAmount: Long,
) {
    companion object {
        @JvmStatic
        fun from(resultRecord: ResultRecord) = LottoResultResponse(
            round = resultRecord.round,
            currentCounterOfThisRound = resultRecord.currentCounterOfThisRound,
            username = resultRecord.username,
            matchedNumbers = resultRecord.matchedNumber,
            numOfMatched = resultRecord.numOfMatched,
            prizeRank = resultRecord.prizeRank,
            prizeAmount = resultRecord.prizeAmount,
        )
    }
}
