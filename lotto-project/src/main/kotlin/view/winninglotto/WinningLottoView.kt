package view.winninglotto

import domain.winninglotto.WinningPrize

/**
 * @author : Unagi_zoso
 * @date : 2023-11-08
 */
class WinningLottoView {
    /**
     * 당첨결과를 몇 번째 로또인지 순서와 함께 출력한다.
     */
    fun showResultWithFormat(order: Int, resultOfLotto: WinningPrize) {
        println(String.format("%6d :  %4s!   당첨금 : %10d KW", order, resultOfLotto.level, resultOfLotto.prizeAmount))
    }

    /**
     * 일치하는 번호를 출력한다.
     */
    fun showMatchedNumbersWith(matchedNumbers: Set<Int>) {
        println("당첨 번호 : ${matchedNumbers.joinToString(" ")}")
    }

    /**
     * 로또 결과 조회 시 안내 문자를 출력합니다.
     */
    fun showCheckAllLottosIntroMessage() {
        println("\n고객님의 로또 결과를 조회합니다.")
    }
}
