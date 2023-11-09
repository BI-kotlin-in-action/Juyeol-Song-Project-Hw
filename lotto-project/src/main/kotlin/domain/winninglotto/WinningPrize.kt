package domain.winninglotto

/**
 * @author : Unagi_zoso
 * @date : 2023-11-07
 */

/**
 * WinningPrize
 * 당첨 등수와 당첨 금액을 가지고 있는 enum class
 */
enum class WinningPrize(val level: String, val matchCount: Int, val prizeAmount: Int) {
    FIRST("1등", 6, 100_000),
    SECOND("2등", 5, 5_000),
    THIRD("3등", 4, 100),
    FOURTH("4등", 3, 5),
    NOPE("낙", 0, 0), ;

    companion object Converter {
        @JvmStatic
        fun getPrize(numOfMatchedNumbers: Int): WinningPrize {
            return WinningPrize.values().find { it.matchCount == numOfMatchedNumbers } ?: NOPE
        }

        /**
         * 당첨 번호와 일치하는 정도(size)들의 리스트를 당첨 보상 (WinningPrize) 리스트로 반환한다.
         */
        @JvmStatic
        fun convertToPrizeList(matchedSizeList: List<Int>): List<WinningPrize> {
            return matchedSizeList.map(::getPrize)
        }
    }
}
