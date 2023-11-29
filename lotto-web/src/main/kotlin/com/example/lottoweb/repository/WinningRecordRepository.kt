package com.example.lottoweb.repository

import com.example.lottoweb.domain.model.WinningRecord
import org.springframework.data.jpa.repository.JpaRepository

interface WinningRecordRepository : JpaRepository<WinningRecord, Long> {
    fun findByRound(round: Int): WinningRecord
}
