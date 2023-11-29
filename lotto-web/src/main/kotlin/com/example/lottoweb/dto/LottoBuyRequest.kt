package com.example.lottoweb.dto

import com.example.lottoweb.utils.annotation.ValidLottoBuyRequest
import jakarta.validation.constraints.PositiveOrZero

// 사용자가 로또를 구매할 때 요청하는 DTO
@ValidLottoBuyRequest
data class LottoBuyRequest(
    @field:PositiveOrZero
    val numOfManual: Int,
    // 커스텀 유효성 검사 시 manualLottoNumbers의 size를 사용하기에 기본적으로 emptyList()로 초기화하였다.
    val manualLottoNumbers: List<String> = emptyList(),
    @field:PositiveOrZero
    val numOfAuto: Int,
)
