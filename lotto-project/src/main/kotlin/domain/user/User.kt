package domain.user

import domain.lotto.Lotto
import domain.lottogenerator.LottoGenerator.Companion.generateLottos
import domain.winninglotto.WinningPrize
import utils.lottomachine.AutomaticLottoMachineStrategy
import utils.lottomachine.LottoMachineStrategy.Constants.COST_OF_ONE_LOTTO
import utils.lottomachine.ManualLottoMachineStrategy

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */
data class User(
    var currentMoney: Int,
    var earnedMoney: Int = 0,
    val maxNumOfLottos: Int = currentMoney / COST_OF_ONE_LOTTO,
    val lottos: MutableList<Lotto> = mutableListOf(),
) {
    /**
     * 1. 전재산을 털어 로또를 삽니다.
     * 2. 수동으로 구매할 로또의 개수를 입력받습니다.
     * 3. 수동으로 구매할 로또의 개수만큼 로또를 생성합니다.
     * 4. 나머지는 자동으로 생성합니다.
     */
    fun buyManualAndAutonomousLottos(numOfManuals: Int) {
        currentMoney %= COST_OF_ONE_LOTTO

        lottos += generateLottos(ManualLottoMachineStrategy::generateLotto, numOfManuals)
        lottos += generateLottos(AutomaticLottoMachineStrategy::generateLotto, maxNumOfLottos - numOfManuals)
    }

    /**
     * 당첨 결과를 통해 총 당첨금액을 지급받는다.
     */
    fun receiveAllPrizeAmount(resultsOfAllLottos: List<WinningPrize>) {
        earnedMoney += resultsOfAllLottos.sumOf(WinningPrize::prizeAmount)
    }
}
