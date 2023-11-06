package winninglotto

/**
 * @author : Unagi_zoso
 * @date : 2023-11-07
 */
enum class WinningPrize(val level: String, val prizeAmount: Int) {
    FIRST("1등", 100_000),
    SECOND("2등", 5_000),
    THIRD("3등", 100),
    FOURTH("4등", 5),
    NOPE("낙", 0),
}
