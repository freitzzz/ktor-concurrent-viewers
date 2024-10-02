package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.mustache.MustacheContent
import io.ktor.server.response.*
import io.ktor.server.routing.*

var counter = 0

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(
                MustacheContent(
                    "index.html",
                    mapOf("counter" to ++counter)
                )
            )
        }
    }
}
