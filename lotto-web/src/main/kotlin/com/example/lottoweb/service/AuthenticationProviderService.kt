package com.example.lottoweb.service

import com.example.lottoweb.exception.PasswordNotMatchException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

/**
 * @author Unagi_zoso
 * @since 2023-11-23
 */
// AuthenticationProvider를 구현하여 사용자가 입력한 username과 password를 검증한다.
@Service
class AuthenticationProviderService(
    private val principalDetailsService: PrincipalDetailsService,
    private val passwordEncoder: BCryptPasswordEncoder,
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        val username = authentication?.name
        val password = authentication?.credentials.toString()

        val principalDetails = principalDetailsService.loadUserByUsername(username)

        if (passwordEncoder.matches(password, principalDetails.password)) {
            return UsernamePasswordAuthenticationToken(username, password, principalDetails.authorities)
        }
        throw PasswordNotMatchException()
    }

    override fun supports(authentication: Class<*>?) = authentication == UsernamePasswordAuthenticationToken::class.java
}
