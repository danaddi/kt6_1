package com.example.kt6_2.di

import com.example.kt6_2.data.api.NobelPrizeApi
import com.example.kt6_2.data.repository.NobelPrizeRepositoryImpl
import com.example.kt6_2.domain.repository.NobelPrizeRepository
import com.example.kt6_2.presentation.detail.NobelPrizeDetailViewModel
import com.example.kt6_2.presentation.list.NobelPrizeListViewModel

object AppModule {
    private val api: NobelPrizeApi = NobelPrizeApi()
    private val repository: NobelPrizeRepository = NobelPrizeRepositoryImpl(api)

    fun provideNobelPrizeListViewModel(): NobelPrizeListViewModel {
        return NobelPrizeListViewModel(repository)
    }

    fun provideNobelPrizeDetailViewModel(): NobelPrizeDetailViewModel {
        return NobelPrizeDetailViewModel(repository)
    }
}