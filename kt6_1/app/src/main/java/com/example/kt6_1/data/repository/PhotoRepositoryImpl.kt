package com.example.kt6_1.data.repository

import com.example.kt6_1.data.api.PicsumApi
import com.example.kt6_1.domain.model.Photo
import com.example.kt6_1.domain.repository.PhotoRepository

class PhotoRepositoryImpl(
    private val api: PicsumApi
) : PhotoRepository {
    override suspend fun getPhotos(): Result<List<Photo>> {
        return try {
            val photos = api.getPhotos().map { it.toDomain() }
            Result.success(photos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun PhotoDto.toDomain(): Photo {
        return Photo(
            id = id,
            author = author,
            width = width,
            height = height,
            url = url,
            downloadUrl = downloadUrl
        )
    }
}