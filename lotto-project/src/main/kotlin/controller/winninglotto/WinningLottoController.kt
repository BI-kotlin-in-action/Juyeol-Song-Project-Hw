package controller.winninglotto

import domain.lotto.Lotto
import domain.user.User
import domain.winninglotto.WinningLotto
import domain.winninglotto.WinningPrize
import view.winninglotto.WinningLottoView
import java.util.SortedSet

/**
 * @author : Unagi_zoso
 * @date : 2023-11-08
 */
class WinningLottoController {
    val winningLottoView = WinningLottoView()

    /**
     * otherLotto와 winningLotto의 번호를 비교하여 일치하는 번호를 SortedSet으로 반환한다.
     */
    private fun getMatchedNumberSet(otherLotto: Lotto): SortedSet<Int> {
        return WinningLotto.winningLotto.numbers.intersect(otherLotto.numbers).toSortedSet()
    }

    /**
     * otherLotto 와 winningLotto의 번호를 비교하여 일치하는 번호를 출력하고, 당첨 결과를 반환한다.
     */
    private fun checkLottoResult(otherLotto: Lotto): WinningPrize {
        val matchedNumbers = getMatchedNumberSet(otherLotto)
        winningLottoView.showMatchedNumbersWith(matchedNumbers)
        return WinningPrizeController.getPrize(matchedNumbers.size)
    }

    /**
     * 1. 안내 문자를 출력합니다.
     * 2. User가 가지고 있는 로또를 모두 비교하여 당첨 결과를 출력한다.
     * 3. 당첨 결과는 로또 번호와 당첨 등수, 당첨금을 출력한다.
     * 4. 당첨 결과를 통해 총 당첨금액을 계산한다.
     */
    fun checkAllLottos(user: User) {
        winningLottoView.showCheckAllLottosIntroMessage()
        user.lottos.forEachIndexed { index, lotto ->
            val resultOfLotto = checkLottoResult(lotto)
            winningLottoView.showResultWithFormat(index + 1, resultOfLotto)
            user.earnedMoney += resultOfLotto.prizeAmount
        }
    }
}
