package com.example.lottoweb.domain.model

import com.example.lottoweb.domain.LottoNumbers
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
@Entity(name = "lotto")
data class Lotto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var lottoId: Long? = null,
    // DB에 영속화하는 과정에서 6개의 로또 숫자를 6개씩 따로 따로 저장하기 까다로워 문자열로 저장하였습니다. ( 구분자 ' ' )
    val numbersAsString: String,
) {
    companion object {
        @JvmStatic
        fun from(lottoNumbers: LottoNumbers) = Lotto(
            numbersAsString = lottoNumbers.getNumbersAsString(),
        )
    }
}
