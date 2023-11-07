package user

import lotto.Lotto
import lottomachine.AutomaticLottoMachineStrategy
import lottomachine.ManualLottoMachineStrategy
import winninglotto.WinningLotto

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */
class User {
    var currentMoney = 0
    var earnedMoney = 0
    var lottos = listOf<Lotto>()

    init {
        inputStartMoneyWithValidation()
    }

    /**
     * 사용자에게 구매 금액을 입력받는다.
     * 최소 금액은 1000원이다. (LottoMachineStrategy 구현체에서 정의한 최소 금액)
     * 최소 금액보다 적은 금액을 입력하면 다시 입력받는다.
     */
    private fun inputStartMoneyWithValidation() {
        while (true) {
            try {
                currentMoney = System.`in`.bufferedReader().readLine().toInt()
            } catch (e: Exception) {
                println("금액을 확인해주세요.")
                continue
            }
            if (currentMoney < AutomaticLottoMachineStrategy().COST_OF_ONE_LOTTO) {
                println("최소 금액은 ${AutomaticLottoMachineStrategy().COST_OF_ONE_LOTTO} 입니다.")
                continue
            }
            break
        }
    }

    /**
     * 사용자에게 구매할 로또의 수를 입력받는다.
     * 최대 구매 가능 수는 사용자의 현재 금액을 기준으로 계산한다.
     * 사용자가 직접 작성할 로또의 수를 입력받는다.
     * 사용자가 직접 작성한 로또와 자동 생성한 로또를 합쳐서 현재 User의 lottos에 할당한다.
     */
    fun buyLottos() {
        val maxNumOfLottos = ManualLottoMachineStrategy().calculateMaxOfLottosWithMoney(currentMoney)
        currentMoney %= AutomaticLottoMachineStrategy().COST_OF_ONE_LOTTO

        val numOfManual = inputNumOfManualWithValidation(maxNumOfLottos)

        lottos = lottos + ManualLottoMachineStrategy().drawLottos(this, numOfManual)
        lottos = lottos + AutomaticLottoMachineStrategy().drawLottos(this, maxNumOfLottos - numOfManual)
    }

    /**
     * 사용자에게 직접 작성할 로또의 수를 입력받는다.
     * 직접 작성할 로또의 수가 최대 구매 가능 수를 넘어가면 다시 입력받는다.
     * 직접 작성할 로또의 수가 0보다 작으면 다시 입력받는다.
     */
    private fun inputNumOfManualWithValidation(maxNumOfLottos: Int): Int {
        println("최대 구매 가능 수 : $maxNumOfLottos  직접 작성할 로또의 수를 정해주세요.")
        var numOfManual = 0
        while (true) {
            try {
                numOfManual = System.`in`.bufferedReader().readLine().toInt()
            } catch (e: Exception) {
                println("다시 한 번 확인해주세요.")
                continue
            }
            if (numOfManual > maxNumOfLottos) {
                println("최대 구매 가능 수 $maxNumOfLottos,  구매 수량을 확인해주세요.")
                continue
            }
            if (numOfManual < 0) {
                println("0 이상의 수를 입력해주세요.")
                continue
            }
            break
        }
        return numOfManual
    }

    /**
     * 현재 User가 가지고 있는 로또를 모두 출력한다.
     * 로또 번호는 오름차순으로 정렬하여 출력한다.
     * 로또마다 자신의 순번이 앞에 붙어서 출력된다. ex 첫 번째 로또) 1 : 1 2 3 4 5 6
     */
    fun showAllLottos() {
        println("\n고객님의 로또를 조회합니다.")
        lottos.forEachIndexed { index, lotto ->
            println("${index + 1} : " + lotto.numbers.sorted().joinToString(" "))
        }
    }

    /**
     * User가 가지고 있는 로또를 모두 비교하여 당첨 결과를 출력한다.
     * 당첨 결과는 로또 번호와 당첨 등수, 당첨금을 출력한다.
     * 당첨 결과를 통해 총 당첨금액을 계산한다.
     */
    fun checkAllLottos() {
        println("\n고객님의 로또 결과를 조회합니다.")
        lottos.forEachIndexed { index, lotto ->
            print("${index + 1} : ")
            val resultOfLotto = WinningLotto.checkLottoResult(lotto)
            println(String.format("%s! 당첨금 : %d", resultOfLotto.level, resultOfLotto.prizeAmount))
            earnedMoney += resultOfLotto.prizeAmount
        }
    }

    /**
     * 총 당첨금액을 출력한다.
     */
    fun showEarnedMoney() {
        println("\n총 당첨금액은 $earnedMoney 입니다.")
    }
}
