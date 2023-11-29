package com.example.lottoweb.utils.validator

import com.example.lottoweb.domain.LottoNumbers.Companion.NUM_OF_LOTTO_NUMBERS
import com.example.lottoweb.domain.LottoNumbers.Companion.numbersOneToFortyFive
import com.example.lottoweb.dto.LottoBuyRequest
import com.example.lottoweb.exception.InvalidLottoBuyRequestException
import com.example.lottoweb.utils.annotation.ValidLottoBuyRequest
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

// 로또 구매 요청을 검증할 때 사용하는 Validator입니다.
/**
 * @see com.example.lottoweb.utils.annotation.ValidLottoBuyRequest
 */
class LottoBuyRequestValidator : ConstraintValidator<ValidLottoBuyRequest, LottoBuyRequest> {
    override fun isValid(value: LottoBuyRequest, context: ConstraintValidatorContext?): Boolean {
        if (value.numOfManual != value.manualLottoNumbers.size) {
            throw InvalidLottoBuyRequestException()
        }

        return value.manualLottoNumbers.all { isValidLottoNumber(it) }
    }

    private fun isValidLottoNumber(value: String): Boolean {
        val numbers = value.split(" ")

        if (value.isBlank() ||
            numbers.distinct().size != NUM_OF_LOTTO_NUMBERS ||
            numbers.any { it.toInt() !in numbersOneToFortyFive }
        ) {
            throw InvalidLottoBuyRequestException()
        }

        return true
    }
}
