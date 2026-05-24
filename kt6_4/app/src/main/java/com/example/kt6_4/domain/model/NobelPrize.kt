package com.example.kt6_4.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NobelPrize(
    val year: String,
    val category: String,
    val overallMotivation: String = "",
    val laureates: List<Laureate> = emptyList()
)

@Serializable
data class Laureate(
    val id: String,
    val firstName: String,
    val lastName: String = "",
    val motivation: String,
    val share: String
)

@Serializable
data class PrizeSummary(
    val year: String,
    val category: String,
    val laureatesCount: Int
)

@Serializable
data class AuthRequest(
    val username: String,
    val password: String
)

@Serializable
data class AuthResponse(
    val token: String
)

@Serializable
data class ErrorResponse(
    val error: String
)

object PrizeCategories {
    const val PHYSICS = "physics"
    const val CHEMISTRY = "chemistry"
    const val MEDICINE = "medicine"
    const val LITERATURE = "literature"
    const val PEACE = "peace"
    const val ECONOMICS = "economics"

    val ALL = listOf(PHYSICS, CHEMISTRY, MEDICINE, LITERATURE, PEACE, ECONOMICS)
}