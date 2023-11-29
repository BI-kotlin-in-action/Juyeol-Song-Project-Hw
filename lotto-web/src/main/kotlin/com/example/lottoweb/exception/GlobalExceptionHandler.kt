package com.example.lottoweb.exception

import com.example.lottoweb.exception.ResponseConstants.Companion.DUPLICATED_USERNAME
import com.example.lottoweb.exception.ResponseConstants.Companion.FREQUENT_REQUEST
import com.example.lottoweb.exception.ResponseConstants.Companion.INVALID_LOTTO_BUY_REQUEST
import com.example.lottoweb.exception.ResponseConstants.Companion.LOGIN_MISSING
import com.example.lottoweb.exception.ResponseConstants.Companion.NOT_ENOUGH_MONEY
import com.example.lottoweb.exception.ResponseConstants.Companion.PASSWORD_NOT_MATCHED
import com.example.lottoweb.exception.ResponseConstants.Companion.USERNAME_NOT_FOUND
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    @ExceptionHandler(InvalidLottoBuyRequestException::class)
    fun handleInvalidLottoBuyRequestException() = INVALID_LOTTO_BUY_REQUEST

    @ExceptionHandler(FrequentRequestException::class)
    fun handleFrequentRequestException() = FREQUENT_REQUEST

    // validation 예외 처리
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException) =
        ResponseEntity(HttpErrorResponse(HttpStatus.BAD_REQUEST, e.localizedMessage), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleConstraintViolationException(e: MethodArgumentNotValidException) =
        ResponseEntity(HttpErrorResponse(HttpStatus.BAD_REQUEST, e.localizedMessage), HttpStatus.BAD_REQUEST)
}
