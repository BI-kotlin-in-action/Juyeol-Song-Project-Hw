package utils.lottomachine

import domain.lotto.Lotto
import java.util.SortedSet

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */
class ManualLottoMachineStrategy {
    companion object : LottoMachineStrategy {
        /**
         * 외부로부터 번호를 주입 받아 로또를 생성한다
         */
        override fun generateLotto(numbers: SortedSet<Int>?): Lotto {
            return Lotto(numbers ?: throw IllegalArgumentException("로또 번호를 입력해주세요."))
        }
    }
}
