package com.example.lottoweb.domain.model

import com.example.lottoweb.domain.LottoNumbers
import com.example.lottoweb.utils.converter.LottoNumbersConverter
import jakarta.persistence.Convert
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
    @Convert(converter = LottoNumbersConverter::class)
    val lottoNumbers: LottoNumbers,
)
