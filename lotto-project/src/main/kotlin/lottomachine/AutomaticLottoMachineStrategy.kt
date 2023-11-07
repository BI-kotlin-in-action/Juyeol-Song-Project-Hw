package lottomachine

import lotto.Lotto
import kotlin.random.Random

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */

/**
 * AutomaticLottoMachineStrategy
 * LottoMachineStrategy 구현체
 */
class AutomaticLottoMachineStrategy : LottoMachineStrategy {
    override val COST_OF_ONE_LOTTO: Int
        get() = 1000

    /**
     * 자동 로또를 생성하여 반환
     * 1 ~ 45 사이의 숫자를 랜덤으로 선택하여 로또를 생성한다.
     * 중복된 숫자는 제외한다. (Set을 이용)
     */
    override fun generateLotto(): Lotto {
        return Lotto { lotto, numOfLottos ->
            while (lotto.numbers.size != numOfLottos) {
                lotto.numbers.add(Random.nextInt(1, 46))
            }
        }
    }
}
