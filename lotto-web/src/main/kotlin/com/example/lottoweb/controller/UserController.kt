package com.example.lottoweb.controller

import com.example.lottoweb.dto.CurrentBalanceResponse
import com.example.lottoweb.dto.SignUpRequest
import com.example.lottoweb.service.UserService
import com.example.lottoweb.utils.annotation.CoolDown
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author Unagi_zoso
 * @since 2023-11-23
 */
@CoolDown
@Validated
@RequestMapping("/api/users")
@RestController
class UserController(
    private val userService: UserService,
) {
    @PostMapping()
    fun signUp(
        @Valid
        @RequestBody
        signUpRequest: SignUpRequest,
    ): ResponseEntity<Unit> {
        userService.saveUserFromSignUpRequest(signUpRequest)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/username/check")
    fun checkUsername(
        @NotBlank
        @RequestParam("username")
        username: String,
    ): ResponseEntity<Unit> {
        userService.existsByUsername(username)
        return ResponseEntity(OK)
    }

    @GetMapping("/balance")
    fun getCurrentBalance(@AuthenticationPrincipal username: String) =
        ResponseEntity.ok(
            CurrentBalanceResponse(userService.getCurrentBalance(username)),
        )

    @PatchMapping("/balance/withdraw")
    fun withdrawBalance(
        @Positive
        @RequestParam("money")
        money: Long,
        @AuthenticationPrincipal username: String,
    ): ResponseEntity<Unit> {
        userService.withdrawBalance(money, username)
        return ResponseEntity(OK)
    }

    @PatchMapping("/balance/deposit")
    fun depositBalance(
        @Positive
        @RequestParam("money")
        money: Long,
        @AuthenticationPrincipal username: String,
    ): ResponseEntity<Unit> {
        userService.depositBalance(money, username)
        return ResponseEntity(OK)
    }
}
