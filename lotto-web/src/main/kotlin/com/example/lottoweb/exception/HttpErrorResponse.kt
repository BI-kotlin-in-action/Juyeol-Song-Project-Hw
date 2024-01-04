package com.example.lottoweb.exception

import org.hibernate.exception.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException

/**
 * @author Unagi_zoso
 * @since 2023-11-29
 */
// 예외 발생 시 공통 예외 Response
data class HttpErrorResponse(
    val message: String,
) {
    companion object {
        @JvmStatic
        fun toResponseEntity(exception: Exception): ResponseEntity<HttpErrorResponse> {
            val statusCode = when (exception) {
                is HttpErrorException -> exception.statusCode // 커스텀 Http 예외
                is MethodArgumentNotValidException -> BAD_REQUEST
                is HttpMessageNotReadableException -> BAD_REQUEST
                is ConstraintViolationException -> BAD_REQUEST
                else -> HttpStatus.INTERNAL_SERVER_ERROR
            }

            return ResponseEntity(
                HttpErrorResponse(
                    message = exception.message ?: "알 수 없는 에러가 발생했습니다.",
                ),
                statusCode
            )
        }
    }
}
