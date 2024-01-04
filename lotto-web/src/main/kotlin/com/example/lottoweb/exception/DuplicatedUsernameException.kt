package com.example.lottoweb.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST

// username이 중복되었을 때 발생하는 예외
class DuplicatedUsernameException(
    override val statusCode: HttpStatus = BAD_REQUEST,
    override val message: String = "username이 중복되었습니다.",
) : HttpErrorException(statusCode, message)
