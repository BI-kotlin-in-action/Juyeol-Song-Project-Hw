package com.example.lottoweb.domain

/**
 * @author Unagi_zoso
 * @since 2023-11-24
 */
// LottoJobMessageQueue에 담겨진 문자열형태 작업 내용을 객체로 변환하여 저장하는 클래스
data class LottoJob(
    val round: Long,
    val currentCounter: Long,
    val username: String,
    val numbers: LottoNumbers,
) {
    companion object {
        @JvmStatic
        fun from(message: String, delimiter: String = " "): LottoJob {
            val parts = message.split(delimiter)

            val round = parts[0]
            val currentCounter = parts[1]
            val username = parts[2]
            val numbers = parts.subList(3, 9).joinTo(StringBuilder(), " ").toString()

            return LottoJob(round.toLong(), currentCounter.toLong(), username, LottoNumbers.from(numbers))
        }
    }
}
