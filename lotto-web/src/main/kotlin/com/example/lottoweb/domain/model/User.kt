package com.example.lottoweb.domain.model

import com.example.lottoweb.config.SecurityConfig
import com.example.lottoweb.domain.model.Role.ROLE_USER
import com.example.lottoweb.exception.NotEnoughMoneyException
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.persistence.Version
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
@Entity(name = "user")
@Table(
    name = "user",
    uniqueConstraints = [
        UniqueConstraint(name = "username_unique", columnNames = ["username"]),
    ],
)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null,
    val username: String,
    val password: String,
    var balance: Long,
    @Enumerated(EnumType.STRING)
    val role: Role,
    @Version
    val version: Long = 0,
) {
    fun depositBalance(amount: Long) {
        balance += amount
    }

    fun withdrawBalance(amount: Long) {
        if (balance < amount) {
            throw NotEnoughMoneyException()
        }
        balance -= amount
    }

    companion object {
        fun from(
            username: String,
            password: String,
            balance: Long,
            encoder: PasswordEncoder = SecurityConfig().passwordEncoder(),
        ) = User(
            username = username,
            password = encoder.encode(password),
            balance = balance,
            role = ROLE_USER,
        )
    }
}
