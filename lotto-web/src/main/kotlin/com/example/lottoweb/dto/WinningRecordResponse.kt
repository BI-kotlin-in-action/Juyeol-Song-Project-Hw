package com.example.lottoweb.dto

import com.example.lottoweb.domain.LottoNumbers
import com.example.lottoweb.domain.model.WinningRecord
import com.example.lottoweb.utils.jsonbind.LottoNumbersSerializer
import com.fasterxml.jackson.databind.annotation.JsonSerialize

// 당첨번호를 응답할 때 사용하는 DTO
data class WinningRecordResponse(
    val round: Int,
    @field: JsonSerialize(using = LottoNumbersSerializer::class)
    val winningNumbers: LottoNumbers,
) {
    constructor() : this(
        0,
        LottoNumbers.from(listOf()),
    )
    companion object {
        @JvmStatic
        fun from(winningRecord: WinningRecord) = WinningRecordResponse(
            round = winningRecord.round,
            winningNumbers = winningRecord.lottoNumbers,
        )
    }
}
