package domain.lottomachine

import view.lottomachine.ManualLottoMachineView
import java.util.SortedSet

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */
object ManualLottoMachineStrategy : LottoMachineStrategy {
    private val manualLottoMachineView = ManualLottoMachineView()

    /**
     * 직접 로또 번호를 골라 로또를 생성한다
     */
    override fun generateLotto(): SortedSet<Int> {
        return manualLottoMachineView.inputManualNumbersWithValidation()
    }
}
