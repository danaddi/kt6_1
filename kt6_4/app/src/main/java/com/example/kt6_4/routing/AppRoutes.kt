package com.example.kt6_4.routing

import com.example.kt6_4.presentation.PrizeController
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(prizeController: PrizeController) {
    routing {
        get("/") {
            call.respond(mapOf(
                "service" to "Nobel Prize API",
                "version" to "1.0.0",
                "status" to "running"
            ))
        }

        post("/auth/login") {
            prizeController.login(call)
        }

        authenticate("auth-jwt") {
            get("/prizes") {
                prizeController.getAllPrizes(call)
            }

            get("/prizes/{year}/{category}") {
                prizeController.getPrizeDetail(call)
            }

            get("/prizes/{year}/{category}/laureates") {
                prizeController.getLaureates(call)
            }
        }
    }
}