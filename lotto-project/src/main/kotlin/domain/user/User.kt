package domain.user

import domain.lotto.Lotto
import domain.winninglotto.WinningPrize
import utils.lottomachine.LottoMachineStrategy.Constants.COST_OF_ONE_LOTTO

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */
data class User(
    var currentMoney: Int,
    var earnedMoney: Int = 0,
    val maxNumOfLottos: Int = currentMoney / COST_OF_ONE_LOTTO,
) {
    private val lottos: MutableList<Lotto> = mutableListOf()

    fun addLotto(lotto: Lotto) {
        lottos.add(lotto)
    }

    fun getLottos(): List<Lotto> {
        return lottos
    }

    /**
     * 당첨 결과를 통해 총 당첨금액을 지급받는다.
     */
    fun receiveAllPrizeAmount(resultsOfAllLottos: List<WinningPrize>) {
        earnedMoney += resultsOfAllLottos.sumOf(WinningPrize::prizeAmount)
    }
}
