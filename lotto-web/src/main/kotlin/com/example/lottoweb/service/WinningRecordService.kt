package com.example.lottoweb.service

import com.example.lottoweb.domain.AutomaticLottoNumberGenerator
import com.example.lottoweb.domain.model.WinningRecord
import com.example.lottoweb.dto.AvailableRoundResponse
import com.example.lottoweb.dto.WinningRecordResponse
import com.example.lottoweb.repository.WinningRecordRepository
import org.springframework.stereotype.Service

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
@Service
class WinningRecordService(
    private val winningRecordRepository: WinningRecordRepository,
    private val lottoRoundControlService: LottoRoundControlService,
) {
    fun generateNewWinningRecord(currentRound: Int): WinningRecord {
        return winningRecordRepository.save(
            WinningRecord(
                winningRecordId = 0,
                round = currentRound,
                numbersAsString = AutomaticLottoNumberGenerator.generateAutoLottoNumbers().getNumbersAsString(),
            ),
        )
    }

    fun getWinningRecordByRound(round: Int): WinningRecordResponse {
        val winningRecord = winningRecordRepository.findByRound(round)
        return WinningRecordResponse.from(winningRecord)
    }

    // 조회할 수 있는 회차는 현재 회차의 이전 회차 (현재 회차는 당첨 번호가 아직 나오지 않았으므로)
    fun getMaxAvailableRound() =
        AvailableRoundResponse(
            lottoRoundControlService.getAvailableRound(),
        )
}
