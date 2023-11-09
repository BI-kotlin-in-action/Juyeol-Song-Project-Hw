package utils.lottomachine

import domain.lotto.Lotto
import view.lottomachine.ManualLottoMachineView

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */
class ManualLottoMachineStrategy {
    companion object : LottoMachineStrategy {
        private val manualLottoMachineView = ManualLottoMachineView()

        /**
         * 직접 로또 번호를 골라 로또를 생성한다
         */
        override fun generateLotto(): Lotto {
            return Lotto(manualLottoMachineView.inputManualNumbersWithValidation())
        }
    }
}
