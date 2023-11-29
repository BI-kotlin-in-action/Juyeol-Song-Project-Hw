package com.example.lottoweb.controller

import com.example.lottoweb.dto.LottoBuyRequest
import com.example.lottoweb.service.LottoService
import com.example.lottoweb.utils.annotation.CoolDown
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Unagi_zoso
 * @since 2023-11-23
 */
@RequestMapping("/api/lottos")
@RestController
class LottoController(
    private val lottoService: LottoService,
) {
    @CoolDown()
    @PostMapping()
    fun buyLotto(
        @Valid
        @RequestBody
        lottoBuyRequest: LottoBuyRequest,
        @AuthenticationPrincipal username: String,
    ): ResponseEntity<Unit> {
        lottoService.processLottoBuyRequest(lottoBuyRequest, username)
        return ResponseEntity(CREATED)
    }
}
