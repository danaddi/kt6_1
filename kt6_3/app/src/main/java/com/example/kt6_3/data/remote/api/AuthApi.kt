package com.example.kt6_3.data.remote.api

import com.example.kt6_3.data.remote.dto.LoginRequest
import com.example.kt6_3.data.remote.dto.LoginResponse
import com.example.kt6_3.data.remote.dto.UserDto
import com.example.kt6_3.data.remote.dto.UsersResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthApi(private val client: HttpClient) {

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return client.post("auth/login") {
            setBody(loginRequest)
        }.body()
    }

    suspend fun getUsers(token: String): List<UserDto> {
        val response: UsersResponse = client.get("users") {
            header("Authorization", "Bearer $token")
        }.body()
        return response.users
    }

    suspend fun getUserById(token: String, userId: Int): UserDto {
        return client.get("users/$userId") {
            header("Authorization", "Bearer $token")
        }.body()
    }
}