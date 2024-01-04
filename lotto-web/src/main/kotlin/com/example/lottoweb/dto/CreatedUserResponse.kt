package com.example.lottoweb.dto

import com.example.lottoweb.domain.model.User

/**
 * @author Unagi_zoso
 * @since 2024-01-02
 */
data class CreatedUserResponse(
    val username: String,
    val balance: Long,
) {
    companion object {
        fun from(user: User) =
            CreatedUserResponse(user.username, user.balance)
    }
}
