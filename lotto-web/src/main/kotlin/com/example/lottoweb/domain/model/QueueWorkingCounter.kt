package com.example.lottoweb.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

/**
 * @author Unagi_zoso
 * @since 2023-11-26
 */
// 해당 회차의 로또를 생성하는 작업 수를 관리하는 테이블
// 해당 테이블의 용도는 아래 LottoSchedulerService를 참고해주세요.
/**
 * @see com.example.lottoweb.service.LottoSchedulerService
 */
@Entity
data class QueueWorkingCounter(
    @Id
    val queueRound: Int,
    var counter: Long = 0,
) {
    fun increaseCounter() {
        counter++
    }

    fun decreaseCounter() {
        counter--
    }
}
