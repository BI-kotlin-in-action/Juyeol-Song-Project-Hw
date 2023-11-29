package com.example.lottoweb.repository

import com.example.lottoweb.domain.model.QueueWorkingCounter
import jakarta.persistence.LockModeType.PESSIMISTIC_WRITE
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface QueueWorkingCounterRepository : JpaRepository<QueueWorkingCounter, Int> {
    @Lock(PESSIMISTIC_WRITE)
    @Query("SELECT q FROM QueueWorkingCounter q WHERE q.queueRound = :round")
    fun findByRoundWithPessimisticLock(@Param("round") round: Int): QueueWorkingCounter

    fun existsByQueueRoundAndCounter(queueRound: Int, counter: Long): Boolean
}
