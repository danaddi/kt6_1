package com.example.kt6_2.data.repository

import com.example.kt6_2.data.api.NobelPrizeApi
import com.example.kt6_2.domain.model.NobelLaureate
import com.example.kt6_2.domain.model.NobelPrize
import com.example.kt6_2.domain.repository.NobelPrizeRepository

class NobelPrizeRepositoryImpl(
    private val api: NobelPrizeApi
) : NobelPrizeRepository {

    override suspend fun getNobelPrizes(
        year: String?,
        category: String?,
        limit: Int,
        offset: Int
    ): Result<List<NobelPrize>> {
        return try {
            val response = api.getNobelPrizes(year, category, limit, offset)
            val prizes = response.nobelPrizes.map { prizeDto ->
                val laureates = prizeDto.laureates?.map { laureateDto ->
                    NobelLaureate(
                        id = laureateDto.id,
                        knownName = laureateDto.knownName?.en ?:
                        laureateDto.fullName?.en ?: "Неизвестно",
                        fullName = laureateDto.fullName?.en ?:
                        laureateDto.knownName?.en ?: "Неизвестно",
                        motivation = laureateDto.motivation?.en ?: "",
                        portion = laureateDto.portion ?: "",
                        sortOrder = laureateDto.sortOrder,
                        birthDate = laureateDto.birth?.date,
                        birthCountry = laureateDto.birth?.place?.country?.en,
                        birthCity = laureateDto.birth?.place?.city?.en,
                        birthContinent = laureateDto.birth?.place?.continent?.en,
                        deathDate = laureateDto.death?.date,
                        wikipediaUrl = laureateDto.wikipedia?.english ?:
                        laureateDto.links?.href
                    )
                } ?: emptyList()

                NobelPrize(
                    awardYear = prizeDto.awardYear,
                    category = prizeDto.category.en,
                    categoryFullName = prizeDto.categoryFullName?.en ?: prizeDto.category.en,
                    dateAwarded = prizeDto.dateAwarded,
                    prizeAmount = prizeDto.prizeAmount,
                    prizeAmountAdjusted = prizeDto.prizeAmountAdjusted,
                    laureates = laureates
                )
            }
            Result.success(prizes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLaureateById(id: String): Result<NobelLaureate> {
        return try {
            val response = api.getNobelPrizes(limit = 100)
            val laureate = response.nobelPrizes
                .flatMap { prize ->
                    prize.laureates?.map { it to prize } ?: emptyList()
                }
                .firstOrNull { (laureateDto, _) -> laureateDto.id == id }
                ?.let { (laureateDto, prizeDto) ->
                    NobelLaureate(
                        id = laureateDto.id,
                        knownName = laureateDto.knownName?.en ?:
                        laureateDto.fullName?.en ?: "Неизвестно",
                        fullName = laureateDto.fullName?.en ?:
                        laureateDto.knownName?.en ?: "Неизвестно",
                        motivation = laureateDto.motivation?.en ?: "",
                        portion = laureateDto.portion ?: "",
                        sortOrder = laureateDto.sortOrder,
                        birthDate = laureateDto.birth?.date,
                        birthCountry = laureateDto.birth?.place?.country?.en,
                        birthCity = laureateDto.birth?.place?.city?.en,
                        birthContinent = laureateDto.birth?.place?.continent?.en,
                        deathDate = laureateDto.death?.date,
                        wikipediaUrl = laureateDto.wikipedia?.english ?:
                        laureateDto.links?.href,
                        awardYear = prizeDto.awardYear,
                        category = prizeDto.category.en
                    )
                }

            if (laureate != null) {
                Result.success(laureate)
            } else {
                Result.failure(Exception("Лауреат не найден"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}