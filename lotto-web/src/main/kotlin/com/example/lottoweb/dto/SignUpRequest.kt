package com.example.lottoweb.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PositiveOrZero

/**
 * @author Unagi_zoso
 * @since 2023-11-23
 */
// 회원가입 요청 DTO
data class SignUpRequest(
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String,
    @field:PositiveOrZero
    val balance: Long,
)
