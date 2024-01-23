package com.example.lottoweb.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.UNAUTHORIZED

// 로그인이 필요할 때 발생하는 예외
class LoginMissingException(
    override val statusCode: HttpStatus = UNAUTHORIZED,
    override val message: String = "로그인을 해주세요.",
) : HttpErrorException(statusCode, message)
