package com.example.lottoweb.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
// html 화면을 뿌려주는 Controller
@Controller
class WebController {
    @GetMapping("/")
    fun index(): String {
        return "index"
    }

    @GetMapping("/loginform")
    fun loginForm(): String {
        return "loginForm"
    }

    @GetMapping("/signupform")
    fun signForm(): String {
        return "signUpForm"
    }
}
