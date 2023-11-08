import controller.lotto.LottoController
import controller.winninglotto.WinningLottoController
import domain.user.User
import domain.winninglotto.WinningLotto.winningLotto
import view.lotto.LottoView
import view.user.UserView

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */

/**
 * 로또 구매 상황 테스트
 * 1. 사용자 생성 시 초기금액을 입력 받는다.
 * 2. 로또를 구매한다.
 * 3. 구매한 로또를 출력한다.
 * 4. 당첨 번호를 출력한다.
 * 5. 당첨 번호와 구매한 로또를 비교하여 결과와 당첨금을 출력한다.
 * 6. 최종 수익률을 출력한다.
 */
fun main() {
    val user = User()
    val lottoController = LottoController()
    val winningLottoController = WinningLottoController()
    val userView = UserView()
    val lottoView = LottoView()

    lottoController.buyLottos(user)
    userView.showAllLottosWithOrder(user)

    lottoView.showLottoNumbers(winningLotto, prefix = "Winning : ")
    winningLottoController.checkAllLottos(user)

    userView.showEarnedMoney(user)
}
