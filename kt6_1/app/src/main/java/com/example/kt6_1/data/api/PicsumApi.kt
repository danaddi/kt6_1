package com.example.kt6_1.data.api

import com.example.kt6_1.data.model.PhotoDto
import retrofit2.http.GET

interface PicsumApi {
    @GET("v2/list")
    suspend fun getPhotos(): List<PhotoDto>
}