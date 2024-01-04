package com.example.lottoweb.repository

import com.example.lottoweb.domain.model.LottoRoundControl
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author Unagi_zoso
 * @since 2023-11-27
 */
interface LottoRoundControlRepository : JpaRepository<LottoRoundControl, Long> {
    fun findLottoRoundControlByLottoRoundControlId(lottoRoundControlId: Long = 0): LottoRoundControl
}
