package com.example.lottoweb.repository

import com.example.lottoweb.domain.model.ResultRecord
import org.springframework.data.jpa.repository.JpaRepository

interface ResultRecordRepository : JpaRepository<ResultRecord, Long> {
    fun findAllByRoundAndUserId(round: Int, userId: Long): List<ResultRecord>
}
