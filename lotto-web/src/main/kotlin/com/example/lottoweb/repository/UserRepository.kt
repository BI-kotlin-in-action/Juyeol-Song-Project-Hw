package com.example.lottoweb.repository

import com.example.lottoweb.domain.model.User
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

/**
 * @author Unagi_zoso
 * @since 2023-11-23
 */
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM user u WHERE u.username = :username")
    fun findByUsernameWithPessimisticLock(username: String): User?

    fun existsByUsername(username: String): Boolean
}
