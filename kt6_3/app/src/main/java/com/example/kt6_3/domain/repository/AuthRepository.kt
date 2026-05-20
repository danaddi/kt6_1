package com.example.kt6_3.domain.repository

import com.example.kt6_3.data.remote.dto.LoginResponse
import com.example.kt6_3.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val tokenFlow: Flow<String?>
    suspend fun login(username: String, password: String): LoginResponse
    suspend fun getUsers(): List<User>
    suspend fun getUserById(userId: Int): User
    suspend fun logout()
}