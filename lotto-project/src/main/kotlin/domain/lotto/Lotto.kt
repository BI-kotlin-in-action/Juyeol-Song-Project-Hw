package domain.lotto

import java.util.SortedSet

/**
 * Lotto
 * 로또를 생성하는 객체
 * 로또 번호를 가지고 있다. (SortedSet)
 */
data class Lotto(val numbers: SortedSet<Int>) {
    companion object Constants {
        const val NUM_OF_LOTTO_NUMBERS = 6
        const val MIN_LOTTO_NUMBER = 1
        const val MAX_LOTTO_NUMBER = 45
    }
}
