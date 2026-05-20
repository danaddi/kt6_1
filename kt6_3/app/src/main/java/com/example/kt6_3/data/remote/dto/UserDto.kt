package com.example.kt6_3.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    val username: String,
    val email: String,
    val image: String,
    val gender: String,
    val phone: String? = null,
    val age: Int? = null,
    @SerialName("birth_date")
    val birthDate: String? = null,
    @SerialName("blood_group")
    val bloodGroup: String? = null,
    val height: Double? = null,
    val weight: Double? = null,
    @SerialName("eye_color")
    val eyeColor: String? = null,
    val hair: HairDto? = null,
    val address: AddressDto? = null,
    val company: CompanyDto? = null,
    val university: String? = null,
    val role: String? = null
)

@Serializable
data class HairDto(
    val color: String,
    val type: String
)

@Serializable
data class AddressDto(
    val address: String,
    val city: String,
    val state: String? = null,
    @SerialName("state_code")
    val stateCode: String? = null,
    @SerialName("postal_code")
    val postalCode: String,
    val coordinates: CoordinatesDto? = null,
    val country: String
)

@Serializable
data class CoordinatesDto(
    val lat: Double,
    val lng: Double
)

@Serializable
data class CompanyDto(
    val name: String,
    val title: String,
    val department: String,
    val address: AddressDto? = null
)

@Serializable
data class UsersResponse(
    val users: List<UserDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)