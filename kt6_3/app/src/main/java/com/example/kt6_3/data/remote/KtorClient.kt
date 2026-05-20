package com.example.kt6_3.data.remote

import android.content.Context
import com.example.kt6_3.data.local.TokenStorage
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object KtorClient {

    private var token: String? = null

    fun createHttpClient(context: Context): HttpClient {
        val tokenStorage = TokenStorage(context)

        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = false
                    coerceInputValues = true // Добавьте эту строку для обработки null значений
                })
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15000
                connectTimeoutMillis = 10000
                socketTimeoutMillis = 15000
            }

            defaultRequest {
                url("https://dummyjson.com/")
                contentType(ContentType.Application.Json)
                // Здесь мы не можем использовать suspend функцию
                // Токен будет добавляться в каждом конкретном запросе
            }
        }
    }

    suspend fun HttpClient.authorizedGet(path: String, token: String): HttpResponse {
        return this.get(path) {
            header("Authorization", "Bearer $token")
        }
    }
}