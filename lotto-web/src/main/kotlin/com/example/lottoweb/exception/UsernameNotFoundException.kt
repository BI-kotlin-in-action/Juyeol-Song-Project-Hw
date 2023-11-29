package com.example.lottoweb.exception

import org.springframework.http.HttpStatus

class UsernameNotFoundException(
    val statusCode: HttpStatus = HttpStatus.BAD_REQUEST,
    override val message: String = "일치하는 user가 없습니다.",
) : RuntimeException()
