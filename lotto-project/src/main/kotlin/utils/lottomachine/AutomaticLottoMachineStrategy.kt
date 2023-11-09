package utils.lottomachine

import domain.lotto.Lotto
import domain.lotto.Lotto.Constants.MAX_LOTTO_NUMBER
import domain.lotto.Lotto.Constants.MIN_LOTTO_NUMBER
import domain.lotto.Lotto.Constants.NUM_OF_LOTTO_NUMBERS

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */

/**
 * AutomaticLottoMachineStrategy
 * LottoMachineStrategy 구현체
 */
class AutomaticLottoMachineStrategy {
    companion object : LottoMachineStrategy {
        @JvmStatic
        private val numberOneToFortyFive = (MIN_LOTTO_NUMBER..MAX_LOTTO_NUMBER)

        /**
         * 자동 로또를 생성하여 반환
         * 1 ~ 45 사이의 숫자를 랜덤으로 선택하여 로또를 생성한다.
         * 1..45 를 shuffle 한 후, 앞에서 6개를 뽑아서 sortedSet으로 반환한다.
         */
        @JvmStatic
        override fun generateLotto(): Lotto {
            return Lotto(numberOneToFortyFive.shuffled().take(NUM_OF_LOTTO_NUMBERS).toSortedSet())
        }
    }
}
