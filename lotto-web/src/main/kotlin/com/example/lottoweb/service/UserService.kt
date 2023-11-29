package com.example.lottoweb.service

import com.example.lottoweb.domain.model.User
import com.example.lottoweb.dto.SignUpRequest
import com.example.lottoweb.exception.DuplicatedUsernameException
import com.example.lottoweb.exception.UsernameNotFoundException
import com.example.lottoweb.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
@Transactional
@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun findUserByUsername(username: String) =
        userRepository.findByUsername(username) ?: throw UsernameNotFoundException()

    // 비관적 락을 걸어 해당 username 의 User 를 가져온다.
    // user의 balance를 변경할 때 사용한다.
    // 트랜잭션만으로 완벽하게 한 가지 값에 여러 쓰레드가
    // 동시에 변경을 가했을 때 생기는 동시성 문제를 막을 수 있나요?
    fun findUserByUsernameWithLock(username: String) =
        userRepository.findByUsernameWithPessimisticLock(username) ?: throw UsernameNotFoundException()

    fun findUserIdByUsername(username: String) =
        findUserByUsername(username).userId ?: throw UsernameNotFoundException()

    fun existsByUsername(username: String) {
        if (userRepository.existsByUsername(username)) {
            throw DuplicatedUsernameException()
        }
    }

    fun saveUserFromSignUpRequest(signUpRequest: SignUpRequest): User {
        validateDuplicateUsername(signUpRequest.username)
        val user = User.from(
            username = signUpRequest.username,
            password = signUpRequest.password,
            balance = signUpRequest.balance,
        )
        return userRepository.save(user)
    }

    fun validateDuplicateUsername(username: String) {
        if (userRepository.existsByUsername(username)) {
            throw DuplicatedUsernameException()
        }
    }

    fun getCurrentBalance(username: String) =
        findUserByUsername(username).balance

    fun withdrawBalance(money: Long, username: String) {
        findUserByUsernameWithLock(username).withdrawBalance(money)
    }

    fun depositBalance(money: Long, username: String) {
        findUserByUsernameWithLock(username).depositBalance(money)
    }
}
