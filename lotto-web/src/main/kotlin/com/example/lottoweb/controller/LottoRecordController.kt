package com.example.lottoweb.controller

import com.example.lottoweb.service.LottoRecordService
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author Unagi_zoso
 * @since 2023-11-27
 */
@Validated
@RequestMapping("/api/lotto-records")
@RestController
class LottoRecordController(
    private val lottoRecordService: LottoRecordService,
) {
    /**
     * 유저의 로또 기록을 조회합니다.
     *
     * @param username 유저 이름
     * @param round 조회할 회차
     * @return 유저의 로또 기록
     */
    @GetMapping
    fun getUserLottosByRound(
        @NotBlank
        @RequestParam("username")
        username: String,
        @Positive
        @RequestParam("round")
        round: Int,
    ) = ResponseEntity.ok(
        lottoRecordService.getLottoRecordsByUsernameAndRound(username, round),
    )
}
