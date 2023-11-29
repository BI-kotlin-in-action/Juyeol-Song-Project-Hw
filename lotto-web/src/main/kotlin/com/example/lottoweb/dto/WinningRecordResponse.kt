package com.example.lottoweb.dto

import com.example.lottoweb.domain.model.WinningRecord

// 당첨번호를 응답할 때 사용하는 DTO
data class WinningRecordResponse(
    val round: Int,
    val winningNumbers: String,
) {
    companion object {
        @JvmStatic
        fun from(winningRecord: WinningRecord) = WinningRecordResponse(
            round = winningRecord.round,
            winningNumbers = winningRecord.numbersAsString,
        )
    }
}
