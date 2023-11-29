package com.example.lottoweb.domain.model

import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import java.io.Serializable

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
// 구매한 로또 내용을 기록하는 테이블
// lotto_record에서 로또 번호를 관리하는 lotto 테이블을 분리하여 join을 통해 조회합니다.
// 기존 로컬환경 로또 프로그램에서 lotto number 조차도 따로 분리하는 것을 보고 감명 받아
// lotto 번호를 따로 lotto란 테이블에 분리하였는데 그냥 lotto_record에
// lotto 번호를 넣어도 괜찮지 않았을까 생각을 버릴 수 없었습니다.
@Entity(name = "lotto_record")
data class LottoRecord(
    @EmbeddedId
    val lottoRecordId: LottoRecordId? = null,
    val round: Int, // 회차
    val currentCounterOfThisRound: Long, // 이번 회차의 user의 몇 번째 로또인지
)

@Embeddable
data class LottoRecordId(
    val userId: Long,
    val lottoId: Long,
) : Serializable
