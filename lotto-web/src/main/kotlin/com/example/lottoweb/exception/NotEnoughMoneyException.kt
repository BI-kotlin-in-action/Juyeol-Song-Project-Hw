package com.example.lottoweb.exception

import org.springframework.http.HttpStatus

// 돈이 부족할 때 발생하는 예외
class NotEnoughMoneyException(
    val statusCode: HttpStatus = HttpStatus.BAD_REQUEST,
    override val message: String = "돈이 부족합니다.",
) : RuntimeException()
