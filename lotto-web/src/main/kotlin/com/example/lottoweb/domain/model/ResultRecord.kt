package com.example.lottoweb.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
// user 별 매 회차 당첨 결과를 저장하는 테이블
// 주로 조회에 사용되기에 다른 테이블에 중복되는 값이 있지만 허용하였습니다.
@Entity(name = "result_record")
data class ResultRecord(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val resultRecordId: Long? = null,
    // LottoRecord와 중복되지만 매번 조인하는 것보다 비정규화하여 값을 가지고 있는게 낫다 판단하였습니다.
    val round: Int, // 회차
    val currentCounterOfThisRound: Long, // 이번 회차의 몇 번째 로또인지
    val userId: Long,
    val username: String,
    val matchedNumber: String, // 당첨 번호와 일치하는 번호
    val numOfMatched: Int, // 당첨 번호와 일치하는 번호의 개수
    val prizeRank: String, // 당첨 등수
    val prizeAmount: Long, // 당첨 금액
)
