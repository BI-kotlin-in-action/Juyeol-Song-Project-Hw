package com.example.lottoweb.dto

import com.example.lottoweb.domain.model.ResultRecord

// LottoResultResponse를 담는 일급 컬렉션
data class LottoResultsResponse(
    val lottoResults: List<LottoResultResponse>,
) {
    companion object {
        @JvmStatic
        fun from(lottoResults: List<ResultRecord>) = LottoResultsResponse(
            lottoResults = lottoResults.map { LottoResultResponse.from(it) },
        )
    }
}
