package com.example.lottoweb.exception

import org.springframework.http.HttpStatus

// 요청이 너무 빈번할 때 발생하는 예외
/**
 * @see com.example.lottoweb.aspect.CoolDownAspect
 */
class FrequentRequestException(
    val statusCode: HttpStatus = HttpStatus.BAD_REQUEST,
    override val message: String = "요청이 너무 빈번합니다.",
) : RuntimeException()
