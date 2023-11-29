package com.example.lottoweb.exception

import org.springframework.http.HttpStatus

// username이 중복되었을 때 발생하는 예외
class DuplicatedUsernameException(
    val statusCode: HttpStatus = HttpStatus.BAD_REQUEST,
    override val message: String = "username이 중복되었습니다.",
) : RuntimeException()
