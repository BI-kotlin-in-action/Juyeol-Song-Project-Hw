package com.example.lottoweb.exception

import org.springframework.http.ResponseEntity

/**
 * @author Unagi_zoso
 * @since 2023-11-28
 */
// 프로그램에서 관리하는 예외 response 상수
/**
 * 예외들의 상위 클래스를 인자로 받아 GlobalExceptionHandler에서 하나의 메서드로 다 처리할 수 있지만
 * 그러면 user에게 내려가지 않아도 괜찮을 예외가 response로 내려가지 않을까? 생각하여 프로그램에서 예상하고
 * 관리하는 예외만 상수로 시켜 GlobalExceptionHandler에서 처리하도록 하였습니다.
 */
class ResponseConstants {
    companion object {
        val LOGIN_MISSING = ResponseEntity(HttpErrorResponse.of(LoginMissingException()), LoginMissingException().statusCode)
        val NOT_ENOUGH_MONEY = ResponseEntity(HttpErrorResponse.of(NotEnoughMoneyException()), NotEnoughMoneyException().statusCode)
        val DUPLICATED_USERNAME = ResponseEntity(HttpErrorResponse.of(DuplicatedUsernameException()), DuplicatedUsernameException().statusCode)
        val PASSWORD_NOT_MATCHED = ResponseEntity(HttpErrorResponse.of(PasswordNotMatchException()), PasswordNotMatchException().statusCode)
        val INVALID_LOTTO_BUY_REQUEST = ResponseEntity(HttpErrorResponse.of(InvalidLottoBuyRequestException()), InvalidLottoBuyRequestException().statusCode)
        val USERNAME_NOT_FOUND = ResponseEntity(HttpErrorResponse.of(UsernameNotFoundException()), UsernameNotFoundException().statusCode)
        val FREQUENT_REQUEST = ResponseEntity(HttpErrorResponse.of(FrequentRequestException()), FrequentRequestException().statusCode)
    }
}
