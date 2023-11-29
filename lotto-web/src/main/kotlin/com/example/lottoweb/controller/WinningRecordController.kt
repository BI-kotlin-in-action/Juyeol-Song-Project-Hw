package com.example.lottoweb.controller

import com.example.lottoweb.service.WinningRecordService
import com.example.lottoweb.utils.annotation.CoolDown
import jakarta.validation.constraints.Positive
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author Unagi_zoso
 * @since 2023-11-23
 */
@Validated
@RequestMapping("/api/winning-records")
@RestController
class WinningRecordController(
    private val winningRecordService: WinningRecordService,
) {
    /**
     * 현재 조회 가능한 최대 로또 회차를 반환한다.
     */
    @GetMapping("/max-available-round")
    fun getCurrentRound() = ResponseEntity.ok(
        winningRecordService.getMaxAvailableRound(),
    )

    /**
     * round에 해당하는 로또 당첨 번호를 반환한다.
     * @param round 로또 회차
     */
    @CoolDown
    @GetMapping
    fun getWinningRecordByRound(
        @Positive
        @RequestParam("round")
        round: Int,
    ) = ResponseEntity.ok(
        winningRecordService.getWinningRecordByRound(round),
    )
}
