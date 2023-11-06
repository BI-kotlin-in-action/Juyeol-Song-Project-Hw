package lottomachine

import lotto.Lotto
import user.User

interface LottoMachineStrategy {
    val COST_OF_ONE_LOTTO: Int

    fun drawLottos(user: User): List<Lotto>

    fun drawLottos(user: User, numOfLottos: Int = 0): List<Lotto>

    fun generateLotto(): Lotto

    fun calculateMaxOfLottosWithMoney(currentMoney: Int): Int {
        return currentMoney / COST_OF_ONE_LOTTO
    }
}
