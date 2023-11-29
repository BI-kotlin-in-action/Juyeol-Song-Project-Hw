package com.example.lottoweb.domain

import com.example.lottoweb.domain.LottoNumbers.Companion.NUM_OF_LOTTO_NUMBERS
import com.example.lottoweb.domain.LottoNumbers.Companion.numbersOneToFortyFive

/**
 * @author Unagi_zoso
 * @since 2023-11-06
 */
class AutomaticLottoNumberGenerator {
    companion object {
        /**
         * 자동 로또를 생성하여 반환
         * 1 ~ 45 사이의 숫자를 랜덤으로 선택하여 로또를 생성한다.
         * 1..45 를 shuffle 한 후, 앞에서 6개를 뽑아서 sortedSet으로 반환한다.
         * DB에 저장될 것을 고려해 LottoNumbers를 String으로 변환하여 Lotto를 생성한다.
         */
        @JvmStatic
        fun generateAutoLottoNumbers() = LottoNumbers(
            numbers =
            numbersOneToFortyFive
                .shuffled()
                .take(NUM_OF_LOTTO_NUMBERS)
                .toSortedSet(),
        )
    }
}
