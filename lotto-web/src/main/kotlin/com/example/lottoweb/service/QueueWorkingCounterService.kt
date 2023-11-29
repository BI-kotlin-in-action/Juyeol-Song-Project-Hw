package com.example.lottoweb.service

import com.example.lottoweb.domain.LottoJobMessageQueuePool.Companion.getQueueIndexByRound
import com.example.lottoweb.domain.model.QueueWorkingCounter
import com.example.lottoweb.repository.QueueWorkingCounterRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation.READ_COMMITTED
import org.springframework.transaction.annotation.Transactional

@Service
data class QueueWorkingCounterService(
    private val queueWorkingCounterRepository: QueueWorkingCounterRepository,
) {
    // queuePoolSize 만큼의 QueueWorkingCounter 를 생성한다.
    fun initQueueWorkingFlag(queuePoolSize: Int) {
        queueWorkingCounterRepository.saveAll(
            List(queuePoolSize, ::QueueWorkingCounter),
        )
    }

    // 비관적 락을 걸어 해당 round 의 QueueWorkingCounter 를 가져와 값을 올린다.
    // 현재 구매 작업 쓰레드의 작업이 시작함음을 의미한다.
    @Transactional(isolation = READ_COMMITTED)
    fun increaseCounter(queueRound: Int) {
        val queueWorkingCounter = queueWorkingCounterRepository.findByRoundWithPessimisticLock(
            getQueueIndexByRound(queueRound),
        )
        queueWorkingCounter.increaseCounter()
    }

    // 비관적 락을 걸어 해당 round 의 QueueWorkingCounter 를 가져와 값을 내린다.
    // 현재 구매 작업 쓰레드의 작업이 끝났음을 의미한다.
    @Transactional(isolation = READ_COMMITTED)
    fun decreaseCounter(queueRound: Int) {
        val queueWorkingCounter = queueWorkingCounterRepository.findByRoundWithPessimisticLock(
            getQueueIndexByRound(queueRound),
        )
        queueWorkingCounter.decreaseCounter()
    }

    // 현재 round 의 QueueWorkingCounter 의 counter 가 0이 아닌지 확인한다.
    // 현재 round에 구매작업을 하는 경우가 없는지 확인한다.
    // 이 경우에도 락을 걸어줘야 할까요..?
    @Transactional(isolation = READ_COMMITTED)
    fun isNotCounterZero(queueRound: Int) =
        !queueWorkingCounterRepository.existsByQueueRoundAndCounter(
            getQueueIndexByRound(queueRound),
            0,
        )
}
