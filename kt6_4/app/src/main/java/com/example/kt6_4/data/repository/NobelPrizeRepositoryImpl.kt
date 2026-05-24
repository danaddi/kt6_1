package com.example.kt6_4.data.repository


import com.example.kt6_4.data.mock.MockData
import com.example.kt6_4.domain.model.Laureate
import com.example.kt6_4.domain.model.NobelPrize
import com.example.kt6_4.domain.usecase.NobelPrizeRepository

class NobelPrizeRepositoryImpl : NobelPrizeRepository {

    private val prizes: List<NobelPrize> = MockData.getNobelPrizes()

    override fun getAllPrizes(): List<NobelPrize> {
        return prizes
    }

    override fun getPrizeByYearAndCategory(year: String, category: String): NobelPrize? {
        return prizes.find { it.year == year && it.category.equals(category, ignoreCase = true) }
    }

    override fun getLaureatesByYearAndCategory(year: String, category: String): List<Laureate> {
        return getPrizeByYearAndCategory(year, category)?.laureates ?: emptyList()
    }
}