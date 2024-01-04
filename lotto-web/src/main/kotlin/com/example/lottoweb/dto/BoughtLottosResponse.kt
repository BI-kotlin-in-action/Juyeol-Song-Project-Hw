package com.example.lottoweb.dto

import com.example.lottoweb.domain.LottoNumbers
import com.example.lottoweb.utils.jsonbind.LottoNumbersSerializer
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 * @author Unagi_zoso
 * @since 2024-01-02
 */
data class BoughtLottosResponse(
    val boughtLottos: List<BoughtLottoResponse>,
) {
    companion object {
        fun from(boughtLottoResponses: List<BoughtLottoResponse>) = BoughtLottosResponse(
            boughtLottos = boughtLottoResponses,
        )
    }
}
