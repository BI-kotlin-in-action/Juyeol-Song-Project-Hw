package controller.winninglotto

import domain.winninglotto.WinningPrize
import java.util.SortedSet

/**
 * @author : Unagi_zoso
 * @date : 2023-11-08
 */

class WinningPrizeController {
    companion object Convertor {
        fun getPrize(matchedNumbers: SortedSet<Int>): WinningPrize {
            return when (matchedNumbers.size) {
                6 -> WinningPrize.FIRST
                5 -> WinningPrize.SECOND
                4 -> WinningPrize.THIRD
                3 -> WinningPrize.FOURTH
                else -> WinningPrize.NOPE
            }
        }
    }
}
