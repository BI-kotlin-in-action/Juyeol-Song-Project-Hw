package com.example.lottoweb.utils.annotation

import com.example.lottoweb.utils.validator.LottoBuyRequestValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * @author Unagi_zoso
 * @since 2023-11-26
 */

// 로또 구매 요청을 검증할 때 사용하는 어노테이션입니다.
/**
 * @see com.example.lottoweb.utils.validator.LottoBuyRequestValidator
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [LottoBuyRequestValidator::class])
annotation class ValidLottoBuyRequest(
    val message: String = "로또 번호가 유효하지 않습니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
