package com.example.lottoweb.domain

/**
 * @author Unagi_zoso
 * @since 2023-12-28
 */
@JvmInline
value class LottoNumbersList(
    private val numbersList: List<LottoNumbers>,
) {
    fun getNumbersList() = numbersList

    companion object {
        @JvmStatic
        fun from(lottoNumbersList: List<LottoNumbers>) = LottoNumbersList(lottoNumbersList)
    }
}
