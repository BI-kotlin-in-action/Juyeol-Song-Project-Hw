package com.example.lottoweb.service

import com.example.lottoweb.domain.LottoJobQueuePool.Companion.getQueueIndexByRound
import com.example.lottoweb.repository.QueueWorkingCounterRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
data class QueueWorkingCounterService(
    private val queueWorkingCounterRepository: QueueWorkingCounterRepository,
) {
    @Transactional
    fun increaseCounter(queueRound: Int) {
        queueWorkingCounterRepository.findByQueueRound(getQueueIndexByRound(queueRound))
            .increaseCounter()
    }

    @Transactional
    fun decreaseCounter(queueRound: Int) {
        queueWorkingCounterRepository.findById(getQueueIndexByRound(queueRound)).orElseThrow()
            .decreaseCounter()
    }

    // 현재 round 의 QueueWorkingCounter 의 counter 가 0이 아닌지 확인한다.
    // 현재 round에 구매작업을 하는 경우가 없는지 확인한다.
    @Transactional
    fun isNotCounterZero(queueRound: Int) =
        !queueWorkingCounterRepository.existsByQueueRoundAndCounter(
            getQueueIndexByRound(queueRound),
            0,
        )
}
