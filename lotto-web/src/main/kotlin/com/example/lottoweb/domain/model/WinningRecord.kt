package com.example.lottoweb.domain.model

import com.example.lottoweb.domain.LottoNumbers
import com.example.lottoweb.utils.converter.LottoNumbersConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
// 당첨 번호를 저장하는 테이블
@Entity(name = "winning_record")
data class WinningRecord(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "winning_record_id")
    val winningRecordId: Long,
    @Column(name = "round")
    val round: Int, // 회차
    @Convert(converter = LottoNumbersConverter::class)
    val lottoNumbers: LottoNumbers,
)
