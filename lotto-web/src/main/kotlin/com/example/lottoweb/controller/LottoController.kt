package com.example.lottoweb.controller

import com.example.lottoweb.dto.LottoBuyRequest
import com.example.lottoweb.service.LottoBuyService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * @author Unagi_zoso
 * @since 2023-11-23
 */
@RequestMapping("/api/lottos")
@RestController
class LottoController(
    private val lottoBuyService: LottoBuyService,
) {
    @ResponseStatus(CREATED)
    @PostMapping
    fun buyLotto(
        @Valid
        @RequestBody
        lottoBuyRequest: LottoBuyRequest,
        @AuthenticationPrincipal username: String,
    ) = lottoBuyService.processLottoBuyRequest(lottoBuyRequest, username)
}
