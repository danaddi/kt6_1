package com.example.kt6_3.domain.usecase

import com.example.kt6_3.domain.model.User
import com.example.kt6_3.domain.repository.AuthRepository

class GetUsersUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(): Result<List<User>> {
        return try {
            Result.success(repository.getUsers())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}