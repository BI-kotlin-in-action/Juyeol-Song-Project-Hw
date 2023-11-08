package view.user

import domain.lottomachine.LottoMachineStrategy.Constants.COST_OF_ONE_LOTTO
import domain.user.User

/**
 * @author : Unagi_zoso
 * @date : 2023-11-08
 */

class UserView {
    /**
     * 현재 User가 가지고 있는 로또를 모두 출력한다.
     * 로또마다 몇 번째 로또인지 순서가 앞에 붙어서 출력된다. ex 첫 번째 로또) 1 : 1 2 3 4 5 6
     */
    fun showAllLottosWithOrder(user: User) {
        println("\n고객님의 로또를 조회합니다.")
        user.lottos.forEachIndexed { index, lotto ->
            println("${index + 1} : " + lotto.numbers.sorted().joinToString(" "))
        }
    }

    /**
     * 총 당첨금액을 출력한다.
     */
    fun showEarnedMoney(user: User) {
        println("\n총 당첨금액은 ${user.earnedMoney} 입니다.")
    }

    /**
     * 사용자에게 구매 금액을 입력받는다.
     * 최소 금액은 1000원이다. (LottoMachineStrategy 구현체에서 정의한 최소 금액)
     * 최소 금액보다 적은 금액을 입력하면 다시 입력받는다.
     */
    fun inputStartMoneyWithValidation(): Int {
        var currentMoney: Int
        while (true) {
            try {
                currentMoney = System.`in`.bufferedReader().readLine().toInt()
            } catch (e: Exception) {
                println("금액을 확인해주세요.")
                continue
            }
            if (currentMoney < COST_OF_ONE_LOTTO) {
                println("최소 금액은 $COST_OF_ONE_LOTTO 입니다.")
                continue
            }
            return currentMoney
        }
    }
}
