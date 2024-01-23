package com.example.lottoweb.repository

import com.example.lottoweb.domain.model.Lotto
import org.springframework.data.jpa.repository.JpaRepository

interface LottoRepository : JpaRepository<Lotto, Long>
