package domain.lottogenerator

import domain.lotto.Lotto

/**
 * @author : Unagi_zoso
 * @date : 2023-11-09
 */

/**
 * LottoMachineStrategy 전략을 통해 다양한 로또를 만들어냅니다.
 *
 * @see [utils.lottomachine.LottoMachineStrategy]
 * @see [utils.lottomachine.ManualLottoMachineStrategy]
 * @see [utils.lottomachine.AutomaticLottoMachineStrategy]
 */
class LottoGenerator {
    companion object {
        fun generateOneLotto(lottoMachineStrategy: () -> Lotto): Lotto {
            return lottoMachineStrategy()
        }

        fun generateLottos(generateLottoStrategy: () -> Lotto, numOfLottos: Int = 0): List<Lotto> {
            return List(numOfLottos) { generateLottoStrategy() }
        }
    }
}
