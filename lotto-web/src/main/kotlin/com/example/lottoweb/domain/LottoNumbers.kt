package com.example.lottoweb.domain

import java.util.SortedSet

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
// 로또 번호를 저장하는 클래스입니다. SortedSet으로 값을 담으며 ' '를 구분자로 사용하여 문자열로 변환할 수 있습니다.
@JvmInline
value class LottoNumbers(
    val numbers: SortedSet<Int>,
) {
    fun getNumbers(): Set<Int> {
        return numbers
    }

    fun getNumbersAsString(separator: String = " "): String {
        return numbers.joinToString(separator = separator)
    }

    companion object {
        const val NUM_OF_LOTTO_NUMBERS = 6
        const val MIN_LOTTO_NUMBER = 1
        const val MAX_LOTTO_NUMBER = 45

        @JvmStatic
        val numbersOneToFortyFive = (MIN_LOTTO_NUMBER..MAX_LOTTO_NUMBER)

        @JvmStatic
        fun from(numbersAsString: String, separator: String = " ") = LottoNumbers(
            numbers = numbersAsString.split(separator).map { it.toInt() }.toSortedSet(),
        )
    }
}
