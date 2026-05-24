package com.example.kt6_4.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.http.*
import io.ktor.server.response.*

fun Application.configureAuthentication() {
    val jwtSecret = "nobel-prize-api-secret-key-2024-very-long-key"
    val jwtIssuer = "nobel-prize-api"
    val jwtAudience = "nobel-prize-clients"

    install(Authentication) {
        jwt("auth-jwt") {
            realm = "Nobel Prize API"

            verifier(
                JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withIssuer(jwtIssuer)
                    .withAudience(jwtAudience)
                    .build()
            )

            validate { credential ->
                if (credential.payload.subject != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Invalid or expired token")
            }
        }
    }
}