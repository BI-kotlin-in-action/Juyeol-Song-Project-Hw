package com.example.lottoweb.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun httpSecurity(http: HttpSecurity): SecurityFilterChain {
        val allowedUrls = arrayOf("/login", "/loginform", "/signupform", "/api/users", "/api/users/check/username", "/swagger-ui/**")

        return http
            .csrf { it.disable() }
            .cors { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(*allowedUrls).permitAll()
                    .requestMatchers("/", "/api/**").hasAnyAuthority("ROLE_USER")
                    .anyRequest().authenticated() // 그 외 인증 없이 접근 불가
            }
            .formLogin {
                it
                    .loginPage("/loginform")
                    .loginProcessingUrl("/login") // 왜 있는겨?
                    .defaultSuccessUrl("/", true)
            }
            .logout {
                it
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
            }
            .build()
    }
}
