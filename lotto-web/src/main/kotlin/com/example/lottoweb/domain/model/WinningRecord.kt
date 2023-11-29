package com.example.lottoweb.domain.model

import jakarta.persistence.Column
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

    // DB에 영속화하는 과정에서 6개의 로또 숫자를 6개씩 따로 따로 저장하기 까다로워 문자열로 저장하였습니다. ( 구분자 ' ' )
    @Column(name = "numbers_as_string")
    val numbersAsString: String,
)
