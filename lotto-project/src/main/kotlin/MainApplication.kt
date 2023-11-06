import user.User
import winninglotto.WinningLotto

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */
fun main() {
    val user = User()
    user.buyLottos()
    user.showAllLottos()

    WinningLotto.lotto.showLottoNumbers(prefix = "Winning : ")
    user.checkAllLottos()

    user.showEarnedMoney()
}
