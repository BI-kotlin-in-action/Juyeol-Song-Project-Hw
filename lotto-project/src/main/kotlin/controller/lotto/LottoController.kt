package controller.lotto

import domain.lottomachine.AutomaticLottoMachineStrategy
import domain.lottomachine.LottoMachineStrategy
import domain.lottomachine.ManualLottoMachineStrategy
import domain.user.User
import view.lottomachine.ManualLottoMachineView

/**
 * @author : Unagi_zoso
 * @date : 2023-11-08
 */
class LottoController {
    private val manualLottoMachineView = ManualLottoMachineView()

    /**
     * 1. 전재산을 털어 로또를 삽니다.
     * 2. 수동으로 구매할 로또의 개수를 입력받습니다.
     * 3. 수동으로 구매할 로또의 개수만큼 로또를 생성합니다.
     * 4. 나머지는 자동으로 생성합니다.
     */
    fun buyLottos(user: User) {
        user.currentMoney %= LottoMachineStrategy.COST_OF_ONE_LOTTO

        val numOfManuals = manualLottoMachineView.inputNumOfManualWithValidation(user.maxNumOfLottos)
        user.lottos += ManualLottoMachineStrategy.makeLottos(numOfManuals)
        user.lottos += AutomaticLottoMachineStrategy.makeLottos(user.maxNumOfLottos - numOfManuals)
    }
}
