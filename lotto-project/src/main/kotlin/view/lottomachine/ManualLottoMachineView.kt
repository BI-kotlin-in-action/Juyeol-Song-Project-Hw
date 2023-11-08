package view.lottomachine

import domain.lotto.Lotto.Constants.MAX_LOTTO_NUMBER
import domain.lotto.Lotto.Constants.MIN_LOTTO_NUMBER
import domain.lotto.Lotto.Constants.NUM_OF_LOTTO_NUMBERS
import java.util.SortedSet

/**
 * @author : Unagi_zoso
 * @date : 2023-11-08
 */

class ManualLottoMachineView {
    /**
     * 직접 생성할 로또의 수를 유효성 검사와 함께 입력 받는다.
     * 입력 받은 수가 유효하면 입력 받은 수를 반환한다.
     * 유효하지 않으면 다시 입력 받는다.
     *
     * list로 입력 받고 바로 SortedSet으로 변환 시 비용이 있기에
     * distinct(), 로또 최저값, 최고값 검사 이후 반환할 때 SortedSet으로 변환한다.
     */
    fun inputManualNumbersWithValidation(): SortedSet<Int> {
        lateinit var inputNumbers: List<Int>
        while (true) {
            println("원하는 수 $NUM_OF_LOTTO_NUMBERS 개를 중복 없이 공백으로 구분하여 입력해주세요.")
            try {
                inputNumbers = System.`in`.bufferedReader().readLine().split(' ').map { it.toInt() }
            } catch (e: Exception) {
                println("다시 입력해주세요.")
                continue
            }
            if (inputNumbers.distinct().size != NUM_OF_LOTTO_NUMBERS) {
                println("번호를 다시 확인해주세요.")
                continue
            }
            if (inputNumbers.any { it !in (MIN_LOTTO_NUMBER..MAX_LOTTO_NUMBER) }) {
                println("$MIN_LOTTO_NUMBER ~ $MAX_LOTTO_NUMBER 사이의 수가 입력되어야 합니다.")
                continue
            }
            break
        }
        return inputNumbers.toSortedSet()
    }

    /**
     * 사용자에게 직접 작성할 로또의 수를 입력받는다.
     * 직접 작성할 로또의 수가 최대 구매 가능 수를 넘어가면 다시 입력받는다.
     * 직접 작성할 로또의 수가 0보다 작으면 다시 입력받는다.
     */

    fun inputNumOfManualWithValidation(maxNumOfLottos: Int): Int {
        println("최대 구매 가능 수 : $maxNumOfLottos  직접 작성할 로또의 수를 정해주세요.")
        var numOfManual = 0
        while (true) {
            try {
                numOfManual = System.`in`.bufferedReader().readLine().toInt()
            } catch (e: Exception) {
                println("다시 한 번 확인해주세요.")
                continue
            }
            if (numOfManual < 0 || numOfManual > maxNumOfLottos) {
                println("0 이상 $maxNumOfLottos 이하 만큼 구매하실 수 있으세요.")
                continue
            }
            break
        }
        return numOfManual
    }
}
