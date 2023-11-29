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
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
/**
 * user의 잔금을 저장하는 balance를 따로 account라는 테이블로 분리하는게 더 user를 user답게 만들지 않을까 생각했습니다.
 * 초반에 프로그램을 만들 땐 두 테이블로 분리하였습니다. 하지만 결국 user를 통해 참조 (혹은 조인)하여
 * 처리를 하게되는 것을 보고 어색함이 느꼈습니다. 이번의 경우 user의 account가 다수 개를 허용하지 않기에
 * 그냥 user라는 하나의 테이블에서 balance를 다루기로 하였습니다.
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
