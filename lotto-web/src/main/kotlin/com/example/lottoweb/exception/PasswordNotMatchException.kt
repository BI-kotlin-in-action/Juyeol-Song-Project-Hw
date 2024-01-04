package com.example.lottoweb.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.UNAUTHORIZED

// 비밀번호가 일치하지 않을 때 발생하는 예외
class PasswordNotMatchException(
    override val statusCode: HttpStatus = UNAUTHORIZED,
    override val message: String = "비밀번호가 일치하지 않습니다.",
) : HttpErrorException(statusCode, message)
