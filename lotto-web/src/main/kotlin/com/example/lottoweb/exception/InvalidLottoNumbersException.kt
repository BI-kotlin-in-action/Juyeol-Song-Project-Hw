package com.example.lottoweb.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST

// 로또 번호가 유효하지 않을 때 발생하는 예외
class InvalidLottoNumbersException(
    override val statusCode: HttpStatus = BAD_REQUEST,
    override val message: String = "로또 번호를 확인해주세요.",
) : HttpErrorException(statusCode, message)
