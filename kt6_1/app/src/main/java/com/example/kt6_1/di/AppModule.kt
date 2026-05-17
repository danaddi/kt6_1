package com.example.kt6_1.di

import com.example.kt6_1.data.api.PicsumApi
import com.example.kt6_1.data.repository.PhotoRepositoryImpl
import com.example.kt6_1.domain.repository.PhotoRepository
import com.example.kt6_1.presentation.detail.PhotoDetailViewModel
import com.example.kt6_1.presentation.list.PhotoListViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {
    private const val BASE_URL = "https://picsum.photos/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: PicsumApi = retrofit.create(PicsumApi::class.java)

    private val repository: PhotoRepository = PhotoRepositoryImpl(api)

    fun providePhotoListViewModel(): PhotoListViewModel {
        return PhotoListViewModel(repository)
    }

    fun providePhotoDetailViewModel(): PhotoDetailViewModel {
        return PhotoDetailViewModel()
    }
}