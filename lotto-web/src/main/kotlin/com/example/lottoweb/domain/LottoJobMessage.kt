package com.example.lottoweb.domain

/**
 * @author Unagi_zoso
 * @since 2023-11-24
 */
// 로또를 구매하고 작업 내용을 queue에 넣을 때 사용할 메시지입니다.
/**
 *  10분이 되었을 때 LottoJobMessage를 LottoJob으로 변환하며 당첨 작업을 처리하는데
 * 차라리 queue에 넣을 때 LottoJob을 넣게 하여 10분이 되었을 때 변환에 드는 비용을 줄일 수 있지 않았을까 생각이 듭니다.
 */
data class LottoJobMessage(
    val message: String,
) {
    companion object {
        @JvmStatic
        fun from(lottoJob: LottoJob) {
            LottoJobMessage("${lottoJob.round} ${lottoJob.currentCounter} ${lottoJob.username} ${lottoJob.numbers.getNumbersAsString()}")
        }

        @JvmStatic
        fun from(
            currentRound: Int,
            currentCounterOfThisRound: Long,
            username: String,
            lottoNumberString: String,
        ) = LottoJobMessage("$currentRound $currentCounterOfThisRound $username $lottoNumberString")
    }
}
