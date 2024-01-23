package com.example.lottoweb.exception

import com.example.lottoweb.exception.HttpErrorResponseConstants.Companion.DUPLICATED_USERNAME
import com.example.lottoweb.exception.HttpErrorResponseConstants.Companion.INVALID_LOTTO_BUY_REQUEST
import com.example.lottoweb.exception.HttpErrorResponseConstants.Companion.LOGIN_MISSING
import com.example.lottoweb.exception.HttpErrorResponseConstants.Companion.NOT_ENOUGH_MONEY
import com.example.lottoweb.exception.HttpErrorResponseConstants.Companion.PASSWORD_NOT_MATCHED
import com.example.lottoweb.exception.HttpErrorResponseConstants.Companion.USERNAME_NOT_FOUND
import org.hibernate.exception.ConstraintViolationException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * @author Unagi_zoso
 * @since 2023-11-28
 */
// 전역 예외 처리
@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(LoginMissingException::class)
    fun handleLoginMissingException() = LOGIN_MISSING

    @ExceptionHandler(DuplicatedUsernameException::class)
    fun handleDuplicatedUsernameException() = DUPLICATED_USERNAME

    @ExceptionHandler(PasswordNotMatchException::class)
    fun handlePasswordNotMatchException() = PASSWORD_NOT_MATCHED

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUsernameNotFoundException() = USERNAME_NOT_FOUND

    @ExceptionHandler(NotEnoughMoneyException::class)
    fun handleNotEnoughMoneyException() = NOT_ENOUGH_MONEY

    @ExceptionHandler(InvalidLottoNumbersException::class)
    fun handleInvalidLottoBuyRequestException() = INVALID_LOTTO_BUY_REQUEST

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException) =
        HttpErrorResponse.toResponseEntity(e)

    // validation 예외 처리
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException) =
        HttpErrorResponse.toResponseEntity(e)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleConstraintViolationException(e: MethodArgumentNotValidException) =
        HttpErrorResponse.toResponseEntity(e)
}
