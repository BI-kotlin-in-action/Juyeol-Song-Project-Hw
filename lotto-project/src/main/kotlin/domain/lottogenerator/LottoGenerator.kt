package domain.lottogenerator

import domain.lotto.Lotto
import java.util.SortedSet

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
        /**
         * strategy에 따라 로또를 생성해 반환합니다.
         */
        fun generateOneLotto(manualNumbers: SortedSet<Int>? = null, lottoMachineStrategy: (SortedSet<Int>?) -> Lotto): Lotto {
            return lottoMachineStrategy(manualNumbers)
        }
    }
}
