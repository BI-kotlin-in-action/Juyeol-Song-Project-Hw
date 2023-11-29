package com.example.lottoweb.exception

import org.springframework.http.HttpStatus

/**
 * @author Unagi_zoso
 * @since 2023-11-29
 */
// 예외 발생 시 공통 예외 Response
data class HttpErrorResponse(
    val statusCode: HttpStatus,
    val message: String,
) {
    companion object {
        @JvmStatic
        fun of(exception: RuntimeException) =
            HttpErrorResponse(
                statusCode = when (exception) {
                    is LoginMissingException -> HttpStatus.BAD_REQUEST
                    is NotEnoughMoneyException -> HttpStatus.BAD_REQUEST
                    is DuplicatedUsernameException -> HttpStatus.BAD_REQUEST
                    is PasswordNotMatchException -> HttpStatus.BAD_REQUEST
                    is InvalidLottoBuyRequestException -> HttpStatus.BAD_REQUEST
                    is UsernameNotFoundException -> HttpStatus.BAD_REQUEST
                    is FrequentRequestException -> HttpStatus.BAD_REQUEST
                    else -> HttpStatus.INTERNAL_SERVER_ERROR
                },
                message = exception.message ?: "알 수 없는 에러가 발생했습니다.",
            )
    }
}
