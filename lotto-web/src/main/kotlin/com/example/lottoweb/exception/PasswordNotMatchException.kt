package com.example.lottoweb.exception

import org.springframework.http.HttpStatus

// 비밀번호가 일치하지 않을 때 발생하는 예외
class PasswordNotMatchException(
    val statusCode: HttpStatus = HttpStatus.UNAUTHORIZED,
    override val message: String = "비밀번호가 일치하지 않습니다.",
) : RuntimeException()
