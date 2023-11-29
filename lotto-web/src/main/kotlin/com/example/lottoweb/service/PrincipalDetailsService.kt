package com.example.lottoweb.service

import com.example.lottoweb.domain.PrincipalDetails
import com.example.lottoweb.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * @author Unagi_zoso
 * @since 2023-11-23
 */
@Service
class PrincipalDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String?) = PrincipalDetails(
        username?.let { userRepository.findByUsername(it) }
            ?: throw UsernameNotFoundException("유저 정보가 없습니다."),
    )
}
