package com.example.kt6_2.data.api

import com.example.kt6_2.data.model.NobelPrizeResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class NobelPrizeApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = false
            })
        }
    }

    suspend fun getNobelPrizes(
        year: String? = null,
        category: String? = null,
        limit: Int = 25,
        offset: Int = 0
    ): NobelPrizeResponse {
        return client.get("https://api.nobelprize.org/2.1/nobelPrizes") {
            parameter("limit", limit)
            parameter("offset", offset)
            year?.let { parameter("nobelPrizeYear", it) }
            category?.let { parameter("nobelPrizeCategory", it) }
        }.body()
    }
}