package com.example.lottoweb.exception

import com.example.lottoweb.exception.HttpErrorResponse.Companion.toResponseEntity

/**
 * @author Unagi_zoso
 * @since 2023-11-28
 */
// RestControllerAdvice에서 사용할 예외 Response
class HttpErrorResponseConstants {
    companion object {
        val LOGIN_MISSING = toResponseEntity(LoginMissingException())
        val NOT_ENOUGH_MONEY = toResponseEntity(NotEnoughMoneyException())
        val DUPLICATED_USERNAME = toResponseEntity(DuplicatedUsernameException())
        val PASSWORD_NOT_MATCHED = toResponseEntity(PasswordNotMatchException())
        val INVALID_LOTTO_BUY_REQUEST = toResponseEntity(InvalidLottoNumbersException())
        val USERNAME_NOT_FOUND = toResponseEntity(UsernameNotFoundException())
    }
}
