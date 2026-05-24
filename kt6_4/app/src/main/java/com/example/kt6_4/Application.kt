package com.example.kt6_4

import com.example.kt6_4.di.AppModule
import com.example.kt6_4.plugins.configureAuthentication
import com.example.kt6_4.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.http.*
import com.example.kt6_4.routing.configureRouting
import org.slf4j.event.Level

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureAuthentication()
    configureStatusPages()
    configureCallLogging()

    configureRouting(AppModule.prizeController)
}

private fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<IllegalArgumentException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to (cause.message ?: "Bad request"))
            )
        }

        exception<Exception> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to "Internal server error")
            )
        }
    }
}

private fun Application.configureCallLogging() {
    install(CallLogging) {
        level = Level.INFO
    }
}