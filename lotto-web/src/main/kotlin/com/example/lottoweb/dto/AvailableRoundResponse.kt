package com.example.lottoweb.dto

// 사용자에게 조회할 수 있는 최대 라운드를 응답하는 DTO
data class AvailableRoundResponse(
    val currentRound: Int,
)
