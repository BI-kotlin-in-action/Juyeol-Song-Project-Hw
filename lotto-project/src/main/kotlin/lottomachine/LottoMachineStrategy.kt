package lottomachine

import lotto.Lotto
import user.User

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */

/**
 * Strategy Pattern
 */
interface LottoMachineStrategy {
    val COST_OF_ONE_LOTTO: Int

    /**
     * User에게 numOfLottos 개수만큼의 로또를 생성하여 반환
     */
    fun drawLottos(user: User, numOfLottos: Int = 0): List<Lotto> {
        return List(numOfLottos) { generateLotto() }
    }

    /**
     * 전략에 따라 로또를 생성하여 반환
     * @see [lottomachine.AutomaticLottoMachineStrategy.generateLotto]
     * @see [lottomachine.ManualLottoMachineStrategy.generateLotto]
     */
    fun generateLotto(): Lotto

    /**
     * 현재 가진 돈으로 구매할 수 있는 최대 로또 개수를 반환
     */
    fun calculateMaxOfLottosWithMoney(currentMoney: Int): Int {
        return currentMoney / COST_OF_ONE_LOTTO
    }
}
