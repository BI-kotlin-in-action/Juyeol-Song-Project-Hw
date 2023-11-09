package domain.winninglotto

import domain.lotto.Lotto
import domain.lottogenerator.LottoGenerator.Companion.generateOneLotto
import domain.user.User
import utils.lottomachine.AutomaticLottoMachineStrategy
import java.util.SortedSet

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
    @JvmStatic
    val winningLotto = generateOneLotto(AutomaticLottoMachineStrategy::generateLotto)

    /**
     * otherLotto와 당첨 번호를 비교하여 일치하는 번호를 SortedSet으로 반환한다.
     */
    @JvmStatic
    private fun getMatchedNumberSet(otherLotto: Lotto): SortedSet<Int> {
        return (winningLotto.getNumbers() intersect otherLotto.getNumbers()).toSortedSet()
    }

    /**
     * user가 가진 모든 로또를 각 각 당첨 번호와 비교해 일치하는 번호들을 반환한다.
     */
    @JvmStatic
    fun getMatchedNumbersList(user: User): List<SortedSet<Int>> {
        return user.getLottos().map(::getMatchedNumberSet)
    }
}
