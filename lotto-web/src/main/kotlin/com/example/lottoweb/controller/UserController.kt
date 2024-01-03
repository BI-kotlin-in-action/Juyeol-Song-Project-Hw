package com.example.lottoweb.controller

import com.example.lottoweb.dto.SignUpRequest
import com.example.lottoweb.service.UserService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * @author Unagi_zoso
 * @since 2023-11-23
 */
@Validated
@RequestMapping("/api/users")
@RestController
class UserController(
    private val userService: UserService,
) {
    @ResponseStatus(CREATED)
    @PostMapping
    fun signUp(
        @Valid
        @RequestBody
        signUpRequest: SignUpRequest,
    ) = userService.saveUserFromSignUpRequest(signUpRequest)


    @ResponseStatus(NO_CONTENT)
    @GetMapping("/username/check")
    fun checkUsername(
        @NotBlank
        @RequestParam("username")
        username: String,
    ) {
        userService.existsByUsername(username)
    }

    @GetMapping("/balance")
    fun getCurrentBalance(@AuthenticationPrincipal username: String) = ResponseEntity.ok(
        userService.getCurrentBalance(username),
    )

    @ResponseStatus(NO_CONTENT)
    @PatchMapping("/balance/withdraw")
    fun withdrawBalance(
        @Positive
        @RequestParam("money")
        money: Long,
        @AuthenticationPrincipal username: String,
    ) {
        userService.withdrawBalance(money, username)
    }

    @ResponseStatus(NO_CONTENT)
    @PatchMapping("/balance/deposit")
    fun depositBalance(
        @Positive
        @RequestParam("money")
        money: Long,
        @AuthenticationPrincipal username: String,
    ) {
        userService.depositBalance(money, username)
    }
}
