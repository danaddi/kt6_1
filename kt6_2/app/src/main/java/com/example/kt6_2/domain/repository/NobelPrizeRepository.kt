package com.example.kt6_2.domain.repository

import com.example.kt6_2.domain.model.NobelLaureate
import com.example.kt6_2.domain.model.NobelPrize

interface NobelPrizeRepository {
    suspend fun getNobelPrizes(
        year: String? = null,
        category: String? = null,
        limit: Int = 25,
        offset: Int = 0
    ): Result<List<NobelPrize>>

    suspend fun getLaureateById(id: String): Result<NobelLaureate>
}