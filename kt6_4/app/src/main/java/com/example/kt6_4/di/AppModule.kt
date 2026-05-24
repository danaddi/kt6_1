package com.example.kt6_4.di

import com.example.kt6_4.data.repository.NobelPrizeRepositoryImpl
import com.example.kt6_4.domain.usecase.GetPrizesUseCase
import com.example.kt6_4.domain.usecase.NobelPrizeRepository
import com.example.kt6_4.presentation.PrizeController

object AppModule {

    private val repository: NobelPrizeRepository by lazy {
        NobelPrizeRepositoryImpl()
    }

    private val getPrizesUseCase: GetPrizesUseCase by lazy {
        GetPrizesUseCase(repository)
    }

    val prizeController: PrizeController by lazy {
        PrizeController(getPrizesUseCase)
    }
}