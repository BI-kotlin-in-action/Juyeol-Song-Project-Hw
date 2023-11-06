package winninglotto

import lotto.Lotto
import lottomachine.AutomaticLottoMachineStrategy
import winninglotto.WinningPrize.FIRST
import winninglotto.WinningPrize.FOURTH
import winninglotto.WinningPrize.NOPE
import winninglotto.WinningPrize.SECOND
import winninglotto.WinningPrize.THIRD

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */
object WinningLotto {
    val lotto = AutomaticLottoMachineStrategy().generateLotto()

    private fun getMatchedNumbers(otherLotto: Lotto): Set<Int> {
        return lotto.numbers.intersect(otherLotto.numbers)
    }

    fun checkLottoResult(otherLotto: Lotto): WinningPrize {
        val matchedNumbers = getMatchedNumbers(otherLotto)
        showMatchedNumbers(matchedNumbers)
        return when (matchedNumbers.size) {
            6 -> FIRST
            5 -> SECOND
            4 -> THIRD
            3 -> FOURTH
            else -> NOPE
        }
    }

    private fun showMatchedNumbers(matchedNumbers: Set<Int>) {
        print("당첨 번호 : ${matchedNumbers.sorted().joinToString(" ")}  ")
    }
}
