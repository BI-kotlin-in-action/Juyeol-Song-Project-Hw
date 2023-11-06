package lottomachine

import lotto.Lotto
import user.User
import kotlin.random.Random

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */
class AutomaticLottoMachineStrategy : LottoMachineStrategy {
    override val COST_OF_ONE_LOTTO: Int
        get() = 1000

    override fun drawLottos(user: User): List<Lotto> {
        val numOfLottos = user.currentMoney / COST_OF_ONE_LOTTO
        val lottos = mutableListOf<Lotto>()
        repeat(numOfLottos) {
            lottos.add(generateLotto())
        }
        return lottos
    }

    override fun drawLottos(user: User, numOfLottos: Int): List<Lotto> {
        val lottos = mutableListOf<Lotto>()
        repeat(numOfLottos) {
            lottos.add(generateLotto())
        }
        return lottos
    }

    override fun generateLotto(): Lotto {
        return Lotto { lotto, numOfSelection ->
            var nextNum: Int
            while (lotto.numbers.size != numOfSelection) {
                nextNum = Random.nextInt(1, 46)
                lotto.numbers.add(nextNum)
            }
        }
    }
}
