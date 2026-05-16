package com.example.kt6_1.domain.repository

import com.example.kt6_1.domain.model.Photo

interface PhotoRepository {
    suspend fun getPhotos(): Result<List<Photo>>
}