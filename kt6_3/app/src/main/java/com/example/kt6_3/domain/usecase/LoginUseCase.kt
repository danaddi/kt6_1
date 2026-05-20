package com.example.kt6_3.domain.usecase

import com.example.kt6_3.data.remote.dto.LoginResponse
import com.example.kt6_3.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(username: String, password: String): Result<LoginResponse> {
        return try {
            Result.success(repository.login(username, password))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}