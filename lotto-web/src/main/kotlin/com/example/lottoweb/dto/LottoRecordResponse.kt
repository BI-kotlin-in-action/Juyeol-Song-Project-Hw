package com.example.lottoweb.dto

import com.example.lottoweb.domain.model.LottoRecord

// 단일 구매한 로또 정보를 반환 받는 DTO
// 실제론 다수 개의 정보를 반환 받기에 일급컬렉션 느낌의 LottoRecordsResponse를 실질적으로 사용한다.
/**
 * @see com.example.lottoweb.dto.LottoRecordsResponse
 */
data class LottoRecordResponse(
    val round: Int,
    val currentCounterOfThisRound: Long,
    val username: String,
    val lottoNumbers: String,
) {
    companion object {
        @JvmStatic
        fun from(lottoRecord: LottoRecord, username: String, lottoNumbers: String) = LottoRecordResponse(
            round = lottoRecord.round,
            currentCounterOfThisRound = lottoRecord.currentCounterOfThisRound,
            username = username,
            lottoNumbers = lottoNumbers,
        )
    }
}
