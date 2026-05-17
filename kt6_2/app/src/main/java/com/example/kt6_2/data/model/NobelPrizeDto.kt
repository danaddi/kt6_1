package com.example.kt6_2.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NobelPrizeResponse(
    val nobelPrizes: List<NobelPrizeDto>,
    val meta: MetaDto
)

@Serializable
data class MetaDto(
    val offset: Int,
    val limit: Int,
    val count: Int,
    val terms: String? = null
)

@Serializable
data class NobelPrizeDto(
    val awardYear: String,
    val category: CategoryDto,
    val categoryFullName: CategoryFullNameDto? = null,
    val laureates: List<LaureateDto>? = null,
    val prizeAmount: Int? = null,
    val prizeAmountAdjusted: Int? = null,
    val dateAwarded: String? = null
)

@Serializable
data class CategoryDto(
    val en: String,
    val no: String? = null,
    val se: String? = null
)

@Serializable
data class CategoryFullNameDto(
    val en: String,
    val no: String? = null,
    val se: String? = null
)

@Serializable
data class LaureateDto(
    val id: String,
    val knownName: KnownNameDto? = null,
    val fullName: FullNameDto? = null,
    val portion: String? = null,
    val sortOrder: String,
    val motivation: MotivationDto? = null,
    val links: LinksDto? = null,
    val birth: BirthDto? = null,
    val death: DeathDto? = null,
    val wikipedia: WikipediaDto? = null
)

@Serializable
data class KnownNameDto(
    val en: String? = null
)

@Serializable
data class FullNameDto(
    val en: String? = null,
    val se: String? = null,
    val no: String? = null
)

@Serializable
data class MotivationDto(
    val en: String? = null,
    val se: String? = null
)

@Serializable
data class LinksDto(
    val rel: String? = null,
    val href: String? = null,
    val action: String? = null,
    val types: String? = null
)

@Serializable
data class BirthDto(
    val date: String? = null,
    val place: PlaceDto? = null
)

@Serializable
data class DeathDto(
    val date: String? = null,
    val place: PlaceDto? = null
)

@Serializable
data class PlaceDto(
    val city: CityDto? = null,
    val country: CountryDto? = null,
    val continent: ContinentDto? = null
)

@Serializable
data class CityDto(
    val en: String? = null,
    val no: String? = null,
    val se: String? = null
)

@Serializable
data class CountryDto(
    val en: String? = null,
    val no: String? = null,
    val se: String? = null
)

@Serializable
data class ContinentDto(
    val en: String? = null
)

@Serializable
data class WikipediaDto(
    val slug: String? = null,
    val english: String? = null
)