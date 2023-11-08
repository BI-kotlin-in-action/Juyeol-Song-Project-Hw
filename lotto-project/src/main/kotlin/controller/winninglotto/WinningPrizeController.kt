package controller.winninglotto

import domain.winninglotto.WinningPrize

/**
 * @author : Unagi_zoso
 * @date : 2023-11-08
 */

class WinningPrizeController {
    companion object Convertor {
        fun getPrize(numOfMatchedNumbers: Int): WinningPrize {
            return when (numOfMatchedNumbers) {
                6 -> WinningPrize.FIRST
                5 -> WinningPrize.SECOND
                4 -> WinningPrize.THIRD
                3 -> WinningPrize.FOURTH
                else -> WinningPrize.NOPE
            }
        }
    }
}
