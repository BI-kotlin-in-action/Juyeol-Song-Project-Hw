package com.example.lottoweb.domain

/**
 * @author : Unagi_zoso
 * @date : 2023-11-07
 */

/**
 * WinningPrize
 * 당첨 등수와 당첨 금액을 가지고 있는 enum class
 */
enum class WinningPrize(val rank: String, val matchCount: Int, val prizeAmount: Long) {
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
    }
}
