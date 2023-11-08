package domain.lottomachine

import domain.lotto.Lotto
import java.util.SortedSet

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */

/**
 * Strategy Pattern
 * 전략 패턴 인터페이스와 구현체는 domain 패키지에 분류하여도 괜찮을까요?
 */
interface LottoMachineStrategy {
    companion object Constants {
        val COST_OF_ONE_LOTTO: Int
            get() = 1000
    }

    /**
     * User에게 numOfLottos 개수만큼의 로또를 생성하여 반환
     */
    fun makeLottos(numOfLottos: Int = 0): List<Lotto> {
        return List(numOfLottos) { Lotto(generateStrategy = { generateLotto() }) }
    }

    /**
     * 전략에 따라 로또를 생성하여 반환
     * @see [domain.lottomachine.AutomaticLottoMachineStrategy.generateLotto]
     * @see [domain.lottomachine.ManualLottoMachineStrategy.generateLotto]
     */
    fun generateLotto(): SortedSet<Int>
}
