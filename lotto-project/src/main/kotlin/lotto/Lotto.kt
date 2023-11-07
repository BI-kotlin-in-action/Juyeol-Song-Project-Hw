package lotto

/**
 * Lotto
 * 로또를 생성하는 객체
 * 로또 번호를 가지고 있다. (Set)
 * 로또 번호를 생성하는 방법을 전략으로 받아 생성한다. (Strategy Pattern)
 * generateStrategy: @see [lottomachine.LottoMachineStrategy.generateLotto]
 */
class Lotto(numOfLottos: Int = 6, generateStrategy: (lotto: Lotto, numOfLottos: Int) -> Unit) {
    val numbers = mutableSetOf<Int>()

    init {
        generateStrategy(this, numOfLottos)
    }

    /**
     * 로또 번호를 출력한다.
     */
    fun showLottoNumbers(prefix: String = "", postfix: String = "\n") {
        print("$prefix${numbers.sorted().joinToString()}$postfix")
    }
}
