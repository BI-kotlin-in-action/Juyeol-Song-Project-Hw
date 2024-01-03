package com.example.lottoweb.controller

import com.example.lottoweb.service.ResultRecordService
import jakarta.validation.constraints.NotEmpty
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
@RequestMapping("/api/result-records")
@RestController
class ResultRecordController(
    private val resultRecordService: ResultRecordService,
) {
    /**
     * user의 round에 해당하는 로또 결과를 반환한다.
     * @param username 사용자 이름
     * @param round 로또 회차
     */
    @GetMapping
    fun getLottoResults(
        @NotEmpty
        @RequestParam("username")
        username: String,
        @Positive
        @RequestParam("round")
        round: Int,
    ) = ResponseEntity.ok(
        resultRecordService.getResultRecord(round, username),
    )
}
