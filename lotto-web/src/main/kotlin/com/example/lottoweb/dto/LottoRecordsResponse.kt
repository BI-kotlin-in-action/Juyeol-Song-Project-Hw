package com.example.lottoweb.dto

/**
 * @author Unagi_zoso
 * @since 2023-11-27
 */
// LottoRecordResponse를 담는 일급 컬렉션
/**
 * 단순히 DTO 용으로 사용되는 객체인데 컬렉션을 한 번 랩핑했다하여
 * 일급 컬렉션이라고 불러도 적절한지는 모르겠습니다.
 */
data class LottoRecordsResponse(
    val lottoRecords: List<LottoRecordResponse>,
) {
    companion object {
        fun from(lottoRecordResponses: List<LottoRecordResponse>) = LottoRecordsResponse(
            lottoRecords = lottoRecordResponses,
        )
    }
}
