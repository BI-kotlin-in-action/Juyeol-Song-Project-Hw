package lotto

class Lotto(numOfLottos: Int = 6, assignAction: (lotto: Lotto, numOfLottos: Int) -> Unit) {
    val numbers = mutableSetOf<Int>()

    init {
        assignAction(this, numOfLottos)
    }

    fun showLottoNumbers(prefix: String = "", postfix: String = "\n") {
        print("$prefix${numbers.sorted().joinToString()}$postfix")
    }
}
