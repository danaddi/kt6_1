package com.example.kt6_4.domain.usecase

import com.example.kt6_4.domain.model.Laureate
import com.example.kt6_4.domain.model.NobelPrize
import com.example.kt6_4.domain.model.PrizeSummary

interface NobelPrizeRepository {
    fun getAllPrizes(): List<NobelPrize>
    fun getPrizeByYearAndCategory(year: String, category: String): NobelPrize?
    fun getLaureatesByYearAndCategory(year: String, category: String): List<Laureate>
}

class GetPrizesUseCase(private val repository: NobelPrizeRepository) {

    fun getAllPrizes(): List<PrizeSummary> {
        return repository.getAllPrizes().map { prize ->
            PrizeSummary(
                year = prize.year,
                category = prize.category,
                laureatesCount = prize.laureates.size
            )
        }
    }

    fun getPrizeDetail(year: String, category: String): NobelPrize? {
        return repository.getPrizeByYearAndCategory(year, category)
    }

    fun getLaureates(year: String, category: String): List<Laureate> {
        return repository.getLaureatesByYearAndCategory(year, category)
    }
}