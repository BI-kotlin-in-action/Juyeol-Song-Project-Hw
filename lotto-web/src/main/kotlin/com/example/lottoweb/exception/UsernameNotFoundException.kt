package com.example.lottoweb.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST

class UsernameNotFoundException(
    override val statusCode: HttpStatus = BAD_REQUEST,
    override val message: String = "일치하는 user가 없습니다.",
) : HttpErrorException(statusCode, message)
