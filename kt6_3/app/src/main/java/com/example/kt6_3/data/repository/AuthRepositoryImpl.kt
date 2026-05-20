package com.example.kt6_3.data.repository

import com.example.kt6_3.data.local.TokenStorage
import com.example.kt6_3.data.remote.api.AuthApi
import com.example.kt6_3.data.remote.dto.LoginRequest
import com.example.kt6_3.data.remote.dto.LoginResponse
import com.example.kt6_3.domain.model.User
import com.example.kt6_3.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    override val tokenFlow: Flow<String?> = tokenStorage.tokenFlow

    override suspend fun login(username: String, password: String): LoginResponse {
        val loginRequest = LoginRequest(username, password)
        val response = authApi.login(loginRequest)
        response.token?.let { tokenStorage.saveToken(it) }
        return response
    }

    override suspend fun getUsers(): List<User> {
        val token = tokenStorage.tokenFlow.getOrNull()
            ?: throw Exception("No token found")
        return authApi.getUsers(token).map { it.toDomain() }
    }

    override suspend fun getUserById(userId: Int): User {
        val token = tokenStorage.tokenFlow.getOrNull()
            ?: throw Exception("No token found")
        return authApi.getUserById(token, userId).toDomain()
    }

    override suspend fun logout() {
        tokenStorage.clearToken()
    }
}

private fun com.example.kt6_3.data.remote.dto.UserDto.toDomain(): User {
    return User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        username = username,
        email = email,
        image = image,
        gender = gender,
        phone = phone,
        age = age,
        birthDate = birthDate,
        bloodGroup = bloodGroup,
        height = height,
        weight = weight,
        eyeColor = eyeColor,
        hair = hair?.let {
            User.Hair(color = it.color, type = it.type)
        },
        address = address?.let {
            User.Address(
                address = it.address,
                city = it.city,
                state = it.state,
                postalCode = it.postalCode,
                country = it.country
            )
        },
        company = company?.let {
            User.Company(
                name = it.name,
                title = it.title,
                department = it.department
            )
        },
        university = university,
        role = role
    )
}

private suspend fun <T> Flow<T>.getOrNull(): T? {
    var result: T? = null
    this.collect { result = it }
    return result
}