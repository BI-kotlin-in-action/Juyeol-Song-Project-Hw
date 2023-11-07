package lottomachine

import lotto.Lotto

/**
 * @author : Unagi_zoso
 * @date : 2023-11-06
 */
class ManualLottoMachineStrategy : LottoMachineStrategy {
    override val COST_OF_ONE_LOTTO: Int
        get() = 1000

    /**
     * 직접 로또 번호를 골라 로또를 생성한다
     */
    override fun generateLotto(): Lotto {
        return Lotto { lotto, numOfLottos ->
            val inputNumbers = inputManualNumbersWithValidation(numOfLottos)
            inputNumbers.forEach { num -> lotto.numbers.add(num) }
        }
    }

    /**
     * 직접 생성할 로또의 수를 유효성 검사와 함께 입력 받는다.
     * 입력 받은 수가 유효하면 입력 받은 수를 반환한다.
     * 유효하지 않으면 다시 입력 받는다.
     */
    private fun inputManualNumbersWithValidation(numOfSelection: Int): Set<Int> {
        var inputNumbers = setOf<Int>()
        while (true) {
            println("원하는 수 6개를 중복 없이 공백으로 구분하여 입력해주세요.")
            try {
                inputNumbers = System.`in`.bufferedReader().readLine().split(' ').map { it.toInt() }.toSet()
            } catch (e: Exception) {
                println("다시 입력해주세요.")
            }
            if (validateManualNumber(inputNumbers, numOfSelection)) break
        }
        return inputNumbers
    }

    /**
     * 직접 생성할 로또의 수가 유효한지 검사한다.
     */
    private fun validateManualNumber(
        inputNumber: Set<Int>,
        numOfSelection: Int,
    ): Boolean {
        if (inputNumber.size != numOfSelection) {
            println("번호를 다시 확인해주세요.")
        }
        if (inputNumber.any { it !in 1..45 }) {
            println("1 ~ 45 사이의 수가 입력되어야 합니다.")
        }

        if (inputNumber.size == numOfSelection && inputNumber.all { it in 1..45 }) {
            return true
        }
        return false
    }
}
