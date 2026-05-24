package com.example.kt6_4.presentation

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtUtils {
    fun generateToken(
        username: String,
        secret: String,
        issuer: String,
        audience: String,
        expirationMinutes: Long = 30
    ): String {
        return JWT.create()
            .withSubject(username)
            .withIssuer(issuer)
            .withAudience(audience)
            .withExpiresAt(Date(System.currentTimeMillis() + expirationMinutes * 60 * 1000))
            .sign(Algorithm.HMAC256(secret))
    }
}