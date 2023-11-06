package lottomachine

import lotto.Lotto
import user.User

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */
class ManualLottoMachineStrategy : LottoMachineStrategy {
    override val COST_OF_ONE_LOTTO: Int
        get() = 1000

    override fun drawLottos(user: User): List<Lotto> {
        val numOfManual = user.currentMoney / COST_OF_ONE_LOTTO
        val lottos = mutableListOf<Lotto>()
        repeat(numOfManual) {
            lottos.add(generateLotto())
        }
        return lottos
    }

    override fun drawLottos(user: User, numOfLottos: Int): List<Lotto> {
        val lottos = mutableListOf<Lotto>()
        repeat(numOfLottos) {
            lottos.add(generateLotto())
            lottos.last().showLottoNumbers()
        }
        return lottos
    }

    override fun generateLotto(): Lotto {
        return Lotto { lotto, numOfSelection ->
            val inputNumbers = inputManualNumbers(numOfSelection)
            inputNumbers.forEach { num -> lotto.numbers.add(num) }
        }
    }

    private fun inputManualNumbers(numOfSelection: Int): Set<Int> {
        val inputNumbers = System.`in`.bufferedReader().readLine().split(' ').map { it.toInt() }.toSet()
        return inputNumbers
    }
}
