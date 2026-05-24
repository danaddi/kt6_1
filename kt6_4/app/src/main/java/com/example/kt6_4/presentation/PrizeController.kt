package com.example.kt6_4.presentation

import com.example.kt6_4.domain.model.AuthRequest
import com.example.kt6_4.domain.model.AuthResponse
import com.example.kt6_4.domain.model.ErrorResponse
import com.example.kt6_4.domain.model.PrizeCategories
import com.example.kt6_4.domain.usecase.GetPrizesUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class PrizeController(private val getPrizesUseCase: GetPrizesUseCase) {

    private val users = mapOf(
        "admin" to "nobel2024",
        "user" to "password123"
    )

    private val jwtSecret = "nobel-prize-api-secret-key-2024-very-long-key"
    private val jwtIssuer = "nobel-prize-api"
    private val jwtAudience = "nobel-prize-clients"

    suspend fun login(call: ApplicationCall) {
        val request = call.receive<AuthRequest>()

        if (users[request.username] == request.password) {
            val token = JwtUtils.generateToken(
                username = request.username,
                secret = jwtSecret,
                issuer = jwtIssuer,
                audience = jwtAudience
            )
            call.respond(AuthResponse(token = token))
        } else {
            call.respond(
                HttpStatusCode.Unauthorized,
                ErrorResponse(error = "Invalid username or password")
            )
        }
    }

    suspend fun getAllPrizes(call: ApplicationCall) {
        val prizes = getPrizesUseCase.getAllPrizes()
        call.respond(prizes)
    }

    suspend fun getPrizeDetail(call: ApplicationCall) {
        val year = call.parameters["year"] ?: throw IllegalArgumentException("Year is required")
        val category = call.parameters["category"] ?: throw IllegalArgumentException("Category is required")

        if (category.lowercase() !in PrizeCategories.ALL) {
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(error = "Invalid category. Valid categories: ${PrizeCategories.ALL.joinToString(", ")}")
            )
            return
        }

        val prize = getPrizesUseCase.getPrizeDetail(year, category)
        if (prize != null) {
            call.respond(prize)
        } else {
            call.respond(
                HttpStatusCode.NotFound,
                ErrorResponse(error = "Nobel Prize not found for year: $year, category: $category")
            )
        }
    }

    suspend fun getLaureates(call: ApplicationCall) {
        val year = call.parameters["year"] ?: throw IllegalArgumentException("Year is required")
        val category = call.parameters["category"] ?: throw IllegalArgumentException("Category is required")

        if (category.lowercase() !in PrizeCategories.ALL) {
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(error = "Invalid category. Valid categories: ${PrizeCategories.ALL.joinToString(", ")}")
            )
            return
        }

        val laureates = getPrizesUseCase.getLaureates(year, category)
        if (laureates.isNotEmpty()) {
            call.respond(laureates)
        } else {
            call.respond(
                HttpStatusCode.NotFound,
                ErrorResponse(error = "Laureates not found for year: $year, category: $category")
            )
        }
    }
}