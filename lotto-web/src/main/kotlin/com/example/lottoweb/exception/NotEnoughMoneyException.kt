package com.example.lottoweb.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST

// 돈이 부족할 때 발생하는 예외
class NotEnoughMoneyException(
    override val statusCode: HttpStatus = BAD_REQUEST,
    override val message: String = "돈이 부족합니다.",
) : HttpErrorException(statusCode, message)
