package view.winninglotto

import domain.winninglotto.WinningPrize
import domain.winninglotto.WinningPrize.Converter.getPrize
import java.util.SortedSet

/**
 * @author : Unagi_zoso
 * @date : 2023-11-08
 */
class WinningLottoView {
    /**
     * 당첨결과를 몇 번째 로또인지 순서와 함께 출력한다.
     */
    private fun showPrizeInfoWithOrder(order: Int, resultOfLotto: WinningPrize) {
        println(String.format("%6d :  %4s!   당첨금 : %10d KW", order, resultOfLotto.level, resultOfLotto.prizeAmount))
    }

    /**
     * 일치하는 번호를 출력한다.
     */
    private fun showMatchedNumbersWith(matchedNumbers: Set<Int>) {
        println("당첨 번호 : ${matchedNumbers.joinToString(" ")}")
    }

    /**
     * 모든 로또 결과에 대하여 일치하는 번호와 당첨 정보를 출력합니다.
     */
    fun showResultsOfAllLottos(resultsOfAllLottos: List<SortedSet<Int>>) {
        println("\n고객님의 로또 결과를 조회합니다.")
        resultsOfAllLottos.forEachIndexed { index, resultOfLotto ->
            showMatchedNumbersWith(resultOfLotto)
            showPrizeInfoWithOrder(index + 1, getPrize(resultOfLotto.size))
        }
    }
}
