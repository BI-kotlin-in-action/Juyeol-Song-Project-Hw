package com.example.lottoweb.exception

import org.springframework.http.HttpStatus

// 로그인이 필요할 때 발생하는 예외
class LoginMissingException(
    val statusCode: HttpStatus = HttpStatus.UNAUTHORIZED,
    override val message: String = "로그인을 해주세요.",
) : RuntimeException()
