package com.example.lottoweb.domain

import com.example.lottoweb.domain.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * @author Unagi_zoso
 * @since 2023-11-23
 */
// 로그인 시 사용자 정보를 담는 클래스입니다.
class PrincipalDetails(
    private val user: User,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(user.role.value))
    }

    override fun getPassword() = user.password

    override fun getUsername() = user.username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
