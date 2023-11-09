package controller.lotto

import domain.lottogenerator.LottoGenerator.Companion.generateOneLotto
import domain.user.User
import domain.winninglotto.WinningLotto.getMatchedNumbersList
import domain.winninglotto.WinningLotto.winningLotto
import domain.winninglotto.WinningPrize.Converter.convertToPrizeList
import utils.lottomachine.AutomaticLottoMachineStrategy
import utils.lottomachine.LottoMachineStrategy.Constants.COST_OF_ONE_LOTTO
import utils.lottomachine.ManualLottoMachineStrategy
import view.lotto.LottoView
import view.manuallotto.ManualLottoView
import view.user.UserView
import view.winninglotto.WinningLottoView

/**
 * @author : Unagi_zoso
 * @date : 2023-11-08
 */

class LottoController {
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
        val userView = UserView()
        val lottoView = LottoView()
        val winningLottoView = WinningLottoView()

        val startMoney = userView.inputStartMoneyWithValidation()
        val user = User(currentMoney = startMoney)

        buyLottos(user)
        userView.showAllLottosWithOrder(user)

        lottoView.showLottoNumbers(winningLotto, prefix = "Winning : ")

        val matchedNumbersList = getMatchedNumbersList(user)
        winningLottoView.showResultsOfAllLottos(matchedNumbersList)

        user.receiveAllPrizeAmount(convertToPrizeList(matchedNumbersList.map { it.size }))
        userView.showEarnedMoney(user)
    }

    /**
     * LottoController를 작성하며
     * 반복되는 로직은 없지만 코드가 길어지고 의미있는 단위로 묶을 수도 있을 것 같은데
     * 가독성의 관점에서 LottoController.start() 내부의 코드를 함수로 나눠도 괜찮을까요?
     * 예 ) LottoController.buyLottos() 에선 로또 구매하는 행위를 정의한다.
     */
    private fun buyLottos(user: User) {
        val manualLottoView = ManualLottoView()

        user.currentMoney %= COST_OF_ONE_LOTTO
        val numOfManuals = manualLottoView.inputNumOfManualWithValidation(user.maxNumOfLottos)
        repeat(numOfManuals) {
            user.addLotto(generateOneLotto(ManualLottoMachineStrategy::generateLotto))
        }
        repeat(user.maxNumOfLottos - numOfManuals) {
            user.addLotto(generateOneLotto(AutomaticLottoMachineStrategy::generateLotto))
        }
    }
}
