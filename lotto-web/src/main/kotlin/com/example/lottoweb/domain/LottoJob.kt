package com.example.lottoweb.domain

/**
 * @author Unagi_zoso
 * @since 2023-11-24
 */
data class LottoJob(
    val round: Int,
    val currentCounter: Long,
    val username: String,
    val numbers: LottoNumbers,
)
