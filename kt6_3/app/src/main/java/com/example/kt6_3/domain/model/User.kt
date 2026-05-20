package com.example.kt6_3.domain.model

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val image: String,
    val gender: String,
    val phone: String? = null,
    val age: Int? = null,
    val birthDate: String? = null,
    val bloodGroup: String? = null,
    val height: Double? = null,
    val weight: Double? = null,
    val eyeColor: String? = null,
    val hair: Hair? = null,
    val address: Address? = null,
    val company: Company? = null,
    val university: String? = null,
    val role: String? = null
) {
    data class Hair(
        val color: String,
        val type: String
    )

    data class Address(
        val address: String,
        val city: String,
        val state: String? = null,
        val postalCode: String,
        val country: String
    )

    data class Company(
        val name: String,
        val title: String,
        val department: String
    )
}