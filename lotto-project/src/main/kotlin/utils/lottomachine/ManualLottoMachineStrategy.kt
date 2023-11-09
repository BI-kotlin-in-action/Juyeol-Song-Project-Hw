package utils.lottomachine

import domain.lotto.Lotto
import view.manuallotto.ManualLottoView

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */

class ManualLottoMachineStrategy {
    companion object : LottoMachineStrategy {
        @JvmStatic
        private var manualLottoView = ManualLottoView()

        /**
         * 외부로부터 번호를 주입 받아 로또를 생성한다
         */
        @JvmStatic
        override fun generateLotto(): Lotto {
            return Lotto(manualLottoView.inputManualNumbersWithValidation())
        }
    }
}
