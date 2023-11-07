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

/**
 * WinningLotto
 * 당첨 번호를 가지고 있는 객체 (Singleton)
 * 당첨 번호를 생성하고, 당첨 번호와 비교하여 당첨 결과를 반환한다.
 */
object WinningLotto {
    val winningLotto = AutomaticLottoMachineStrategy().generateLotto()

    /**
     * otherLotto와 winningLotto의 번호를 비교하여 일치하는 번호를 set으로 반환한다.
     */
    private fun getMatchedNumberSet(otherLotto: Lotto): Set<Int> {
        return winningLotto.numbers.intersect(otherLotto.numbers)
    }

    /**
     * otherLotto 와 winningLotto의 번호를 비교하여 일치하는 번호를 출력하고, 당첨 결과를 반환한다.
     */
    fun checkLottoResult(otherLotto: Lotto): WinningPrize {
        val matchedNumbers = getMatchedNumberSet(otherLotto)
        showMatchedNumbers(matchedNumbers)
        return when (matchedNumbers.size) {
            6 -> FIRST
            5 -> SECOND
            4 -> THIRD
            3 -> FOURTH
            else -> NOPE
        }
    }

    /**
     * 일치하는 번호를 출력한다.
     */
    private fun showMatchedNumbers(matchedNumbers: Set<Int>) {
        print("당첨 번호 : ${matchedNumbers.sorted().joinToString(" ")}  ")
    }
}
