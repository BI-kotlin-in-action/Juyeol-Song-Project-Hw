package utils.lottomachine

import domain.lotto.Lotto
import java.util.SortedSet

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */

/**
 * Strategy Pattern
 */
interface LottoMachineStrategy {
    companion object Constants {
        const val COST_OF_ONE_LOTTO = 1000
    }

    /**
     * 전략에 따라 로또를 생성하여 반환
     * 외부로부터 번호가 생성되어 주입될 시 numbers로 받는다. ex) ManualLottoMachineStrategy
     * @see [utils.lottomachine.AutomaticLottoMachineStrategy.generateLotto]
     * @see [utils.lottomachine.ManualLottoMachineStrategy.generateLotto]
     * @see [domain.lottogenerator.LottoGenerator] - strategy client 객체
     */
    fun generateLotto(numbers: SortedSet<Int>?): Lotto
}
