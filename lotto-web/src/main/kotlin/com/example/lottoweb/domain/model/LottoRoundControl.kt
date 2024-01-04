package com.example.lottoweb.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

/**
 * @author Unagi_zoso
 * @since 2023-11-27
 */
// 로또의 최신화 되는 현재 회차를 관리하는 테이블
// 테이블에는 하나의 레코드만이 시스템 시작 시 들어가게 됩니다.
@Entity(name = "lotto_round_control")
data class LottoRoundControl(
    @Id
    val lottoRoundControlId: Long = 0L,
    var round: Int = 1,
) {
    fun increaseRound() {
        round++
    }
}
