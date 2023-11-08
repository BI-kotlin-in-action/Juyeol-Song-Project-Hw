package domain.winninglotto

/**
 * @author : Unagi_zoso
 * @date : 2023-11-07
 */

/**
 * WinningPrize
 * 당첨 등수와 당첨 금액을 가지고 있는 enum class
 */
enum class WinningPrize(val level: String, val prizeAmount: Int) {
    FIRST("1등", 100_000),
    SECOND("2등", 5_000),
    THIRD("3등", 100),
    FOURTH("4등", 5),
    NOPE("낙", 0),
}
