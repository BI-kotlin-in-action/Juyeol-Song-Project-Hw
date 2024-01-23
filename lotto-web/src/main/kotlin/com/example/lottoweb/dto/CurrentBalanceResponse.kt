package com.example.lottoweb.dto

/**
 * @author Unagi_zoso
 * @since 2023-11-24
 */
// 사용자의 현재 잔액을 응답하는 DTO
data class CurrentBalanceResponse(
    val balance: Long,
)
