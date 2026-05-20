package com.example.kt6_3.domain.usecase

import com.example.kt6_3.domain.model.User
import com.example.kt6_3.domain.repository.AuthRepository

class GetUserDetailUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(userId: Int): Result<User> {
        return try {
            Result.success(repository.getUserById(userId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}