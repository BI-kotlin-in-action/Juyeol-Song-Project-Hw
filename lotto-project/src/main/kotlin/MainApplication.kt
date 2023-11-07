import user.User
import winninglotto.WinningLotto.winningLotto

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */

/**
 * 로또 구매 상황 테스트
 * 1. 사용자에게 구매 금액을 입력받는다.
 * 2. 입력받은 금액으로 로또를 구매한다.
 * 3. 소유한 로또 내용을 출력한다.
 * 4. 당첨 번호를 출력한다. (싱글톤 객체로 처음 생성될 때 자동으로 번호가 생성된다.)
 * 5. 당첨 번호와 구매한 로또를 비교하여 당첨 결과를 출력한다.
 * 6. 당첨 결과를 통해 수익률을 계산하여 출력한다.
 */
fun main() {
    val user = User()
    user.buyLottos()
    user.showAllLottos()

    winningLotto.showLottoNumbers(prefix = "Winning : ")
    user.checkAllLottos()

    user.showEarnedMoney()
}
