package domain.user

import domain.lotto.Lotto
import domain.lottomachine.LottoMachineStrategy.Constants.COST_OF_ONE_LOTTO
import view.user.UserView

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */
data class User(
    var currentMoney: Int = UserView().inputStartMoneyWithValidation(),
    var earnedMoney: Int = 0,
    val maxNumOfLottos: Int = currentMoney / COST_OF_ONE_LOTTO,
    var lottos: List<Lotto> = listOf(),
)
