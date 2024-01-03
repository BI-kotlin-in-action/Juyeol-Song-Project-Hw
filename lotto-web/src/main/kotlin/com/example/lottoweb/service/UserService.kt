package com.example.lottoweb.service

import com.example.lottoweb.domain.model.User
import com.example.lottoweb.dto.CreatedUserResponse
import com.example.lottoweb.dto.CurrentBalanceResponse
import com.example.lottoweb.dto.SignUpRequest
import com.example.lottoweb.exception.DuplicatedUsernameException
import com.example.lottoweb.exception.UsernameNotFoundException
import com.example.lottoweb.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun findUserByUsername(username: String) =
        userRepository.findByUsername(username) ?: throw UsernameNotFoundException()

    fun findUserIdByUsername(username: String) =
        findUserByUsername(username).userId ?: throw UsernameNotFoundException()

    fun existsByUsername(username: String) {
        if (userRepository.existsByUsername(username)) {
            throw DuplicatedUsernameException()
        }
    }

    // 생성된 유저의 정보를 response body에 담고 싶은데 controller에서 response 객체를 생성할까요?
    // 지금 방식처럼 여기서 생성할까요?
    fun saveUserFromSignUpRequest(signUpRequest: SignUpRequest): CreatedUserResponse {
        validateDuplicateUsername(signUpRequest.username)
        val user = User.from(
            username = signUpRequest.username,
            password = signUpRequest.password,
            balance = signUpRequest.balance,
        )
        return CreatedUserResponse.from(userRepository.save(user))
    }

    fun validateDuplicateUsername(username: String) {
        if (userRepository.existsByUsername(username)) {
            throw DuplicatedUsernameException()
        }
    }

    fun getCurrentBalance(username: String) =
        CurrentBalanceResponse(findUserByUsername(username).balance)

    @Transactional(isolation = Isolation.READ_COMMITTED)
    fun withdrawBalance(money: Long, username: String) {
        findUserByUsername(username).withdrawBalance(money)
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    fun depositBalance(money: Long, username: String) {
        findUserByUsername(username).depositBalance(money)
    }
}
