package com.example.lottoweb.exception

import org.springframework.http.HttpStatus

// 로또 번호가 유효하지 않을 때 발생하는 예외
class InvalidLottoNumbersException(
    val statusCode: HttpStatus = HttpStatus.BAD_REQUEST,
    override val message: String = "로또 번호를 확인해주세요.",
) : RuntimeException()
