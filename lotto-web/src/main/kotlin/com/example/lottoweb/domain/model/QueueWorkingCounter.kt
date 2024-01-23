package com.example.lottoweb.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Version

/**
 * @author Unagi_zoso
 * @since 2023-11-26
 */
// 해당 회차의 로또를 생성하는 작업 수를 관리하는 테이블
// 해당 테이블의 용도는 아래 LottoSchedulerService를 참고해주세요.
// resources/initial_data.sql 에서 초기값을 설정해주었습니다.
/**
 * @see com.example.lottoweb.service.LottoSchedulerService
 */
@Entity
data class QueueWorkingCounter(
    @Id
    val queueRound: Int,
    @Version
    val version: Long = 0,
    var counter: Long = 0,
) {
    fun increaseCounter() {
        counter++
    }

    fun decreaseCounter() {
        counter--
    }
}
