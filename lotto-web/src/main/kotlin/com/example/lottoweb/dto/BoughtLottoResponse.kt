package com.example.lottoweb.dto

import com.example.lottoweb.domain.LottoNumbers
import com.example.lottoweb.utils.jsonbind.LottoNumbersSerializer
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 * @author Unagi_zoso
 * @since 2024-01-02
 */
data class BoughtLottoResponse(
    val round: Int,
    val currentCounterOfThisRound: Long,
    @field: JsonSerialize(using = LottoNumbersSerializer::class)
    val lottoNumbers: LottoNumbers,
) {
    companion object {
        @JvmStatic
        fun from(round: Int, currentCounterOfThisRound: Long, lottoNumbers: LottoNumbers) = BoughtLottoResponse(
            round = round,
            currentCounterOfThisRound = currentCounterOfThisRound,
            lottoNumbers = lottoNumbers,
        )
    }
}
