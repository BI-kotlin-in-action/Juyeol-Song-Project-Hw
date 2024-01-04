package com.example.lottoweb.exception

import org.springframework.http.HttpStatus

/**
 * @author Unagi_zoso
 * @since 2024-01-04
 */
abstract class HttpErrorException(
    open val statusCode: HttpStatus,
    override val message: String,
) : RuntimeException()
