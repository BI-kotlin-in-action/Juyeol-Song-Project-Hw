package com.example.lottoweb.service

import com.example.lottoweb.repository.LottoRoundControlRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Unagi_zoso
 * @since 2023-11-27
 */
// 로또 전체 회차를 관리하는 서비스
@Transactional
@Service
class LottoRoundControlService(
    private val lottoRoundControlRepository: LottoRoundControlRepository,
) {
    fun getLottoRound() =
        lottoRoundControlRepository.findLottoRoundControlByLottoRoundControlId(0).round

    fun increaseRound() {
        val round = lottoRoundControlRepository.findLottoRoundControlByLottoRoundControlId(0)
        round.increaseRound()
    }

    // 조회 가능한 회차를 반환한다.
    fun getAvailableRound() =
        lottoRoundControlRepository.findLottoRoundControlByLottoRoundControlId(0).round - 1
}
