package com.example.kt6_3.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val id: Int,
    val username: String,
    val email: String,
    @SerialName("firstName")
    val firstName: String? = null,
    @SerialName("lastName")
    val lastName: String? = null,
    val gender: String? = null,
    val image: String? = null,
    @SerialName("accessToken")
    val token: String? = null,
    @SerialName("refreshToken")
    val refreshToken: String? = null
)