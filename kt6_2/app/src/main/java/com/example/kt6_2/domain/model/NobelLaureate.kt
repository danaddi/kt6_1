package com.example.kt6_2.domain.model

data class NobelLaureate(
    val id: String,
    val knownName: String,
    val fullName: String,
    val motivation: String,
    val portion: String,
    val sortOrder: String,
    val birthDate: String?,
    val birthCountry: String?,
    val birthCity: String?,
    val birthContinent: String?,
    val deathDate: String?,
    val wikipediaUrl: String?,
    val awardYear: String? = null,
    val category: String? = null
) {
    val displayName: String
        get() = knownName.ifEmpty { fullName }

    val shortMotivation: String
        get() = if (motivation.length > 100) {
            motivation.substring(0, 97) + "..."
        } else {
            motivation
        }

    val birthPlace: String
        get() {
            val parts = listOfNotNull(birthCity, birthCountry).filter { it.isNotEmpty() }
            return parts.joinToString(", ")
        }
}