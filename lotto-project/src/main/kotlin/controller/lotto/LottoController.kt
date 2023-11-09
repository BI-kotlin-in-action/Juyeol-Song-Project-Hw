package controller.lotto

import domain.user.User
import domain.winninglotto.WinningLotto.getMatchedNumbersList
import domain.winninglotto.WinningLotto.winningLotto
import domain.winninglotto.WinningPrize.Converter.convertToPrizeList
import view.lotto.LottoView
import view.lottomachine.ManualLottoMachineView
import view.user.UserView
import view.winninglotto.WinningLottoView

/**
 * @author : Unagi_zoso
 * @date : 2023-11-08
 */

class LottoController {
    private val userView = UserView()
    private val lottoView = LottoView()
    private val manualLottoMachineView = ManualLottoMachineView()
    private val winningLottoView = WinningLottoView()

    /**
     * user가 처음 가지는 돈을 입력 받아 user를 생성한다.
     * user가 직접 작성할 로또의 수를 입력 받고 lotto를 구매한다.
     * user가 구매한 모든 로또의 정보를 순서와 함께 출력한다. 출력형식 ) 1 : 1 2 3 4 5 6
     * 당첨 번호를 출력한다. 출력형식 ) Winning : 1 2 3 4 5 6
     * user가 가지고 있는 모든 로또를 당첨 번호와 비교하여 당첨 번호와 일치하는 번호들을 반환한다.
     * 모든 로또 당첨 결과를 순서와 함께 두 줄로 출력한다. 출력형식)  당첨 번호 : 26
     * 당첨 결과에 따라 돈을 지급한다.                                    1 :  낙!   당첨금 : 0 KW
     * user가 가지고 있는 돈을 출력한다.
     */
    fun start() {
        val startMoney = userView.inputStartMoneyWithValidation()
        val user = User(currentMoney = startMoney)

        val numOfManuals = manualLottoMachineView.inputNumOfManualWithValidation(user.maxNumOfLottos)
        user.buyManualAndAutonomousLottos(numOfManuals)

        userView.showAllLottosWithOrder(user)

        lottoView.showLottoNumbers(winningLotto, prefix = "Winning : ")

        val matchedNumbersList = getMatchedNumbersList(user)
        winningLottoView.showResultsOfAllLottos(matchedNumbersList)

        user.receiveAllPrizeAmount(convertToPrizeList(matchedNumbersList))
        userView.showEarnedMoney(user)
    }
}
