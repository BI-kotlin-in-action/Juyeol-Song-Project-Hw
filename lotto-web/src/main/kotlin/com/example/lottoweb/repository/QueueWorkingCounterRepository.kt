package com.example.lottoweb.repository

import com.example.lottoweb.domain.model.QueueWorkingCounter
import org.springframework.data.jpa.repository.JpaRepository

interface QueueWorkingCounterRepository : JpaRepository<QueueWorkingCounter, Int> {
    fun findByQueueRound(queueRound: Int): QueueWorkingCounter
    fun existsByQueueRoundAndCounter(queueRound: Int, counter: Long): Boolean
}
