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
        inputStartMoney()
    }

    private fun inputStartMoney() {
        currentMoney = System.`in`.bufferedReader().readLine().toInt()
    }

    fun buyLottos() {
        val maxNumOfLottos = ManualLottoMachineStrategy().calculateMaxOfLottosWithMoney(currentMoney)
        if (maxNumOfLottos == 0) {
            println("금액이 부족합니다.")
            return
        }
        currentMoney -= maxNumOfLottos

        val numOfManual = inputNumOfManual(maxNumOfLottos)

        val _lottos = mutableListOf<Lotto>()
        _lottos += ManualLottoMachineStrategy().drawLottos(this, numOfManual)
        _lottos += AutomaticLottoMachineStrategy().drawLottos(this, maxNumOfLottos - numOfManual)
        lottos = _lottos
    }

    private fun inputNumOfManual(maxNumOfLottos: Int): Int {
        println("최대 구매 가능 수 : $maxNumOfLottos  직접 작성할 로또의 수를 정해주세요.")
        val numOfManual = System.`in`.bufferedReader().readLine().toInt()
        return numOfManual
    }

    fun showAllLottos() {
        println("\n고객님의 로또를 조회합니다.")
        lottos.forEachIndexed { index, lotto ->
            println("${index + 1} : " + lotto.numbers.sorted().joinToString(" "))
        }
    }

    fun checkAllLottos() {
        println("\n고객님의 로또 결과를 조회합니다.")
        lottos.forEachIndexed { index, lotto ->
            print("${index + 1} : ")
            val resultOfLotto = WinningLotto.checkLottoResult(lotto)
            println(String.format("%s! 당첨금 : %d", resultOfLotto.level, resultOfLotto.prizeAmount))
            earnedMoney += resultOfLotto.prizeAmount
        }
    }

    fun showEarnedMoney() {
        println("\n총 당첨금액은 $earnedMoney 입니다.")
    }
}
