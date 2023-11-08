package view.lotto

import domain.lotto.Lotto

/**
 * @author : Unagi_zoso
 * @date : 2023-11-08
 */
class LottoView {
    /**
     * 로또 번호를 출력한다.
     */
    fun showLottoNumbers(lotto: Lotto, prefix: String = "", postfix: String = "\n") {
        print("$prefix${lotto.numbers.joinToString()}$postfix")
    }
}
