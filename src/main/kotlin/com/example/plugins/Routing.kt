package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.mustache.MustacheContent
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.send
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.concurrent.fixedRateTimer

fun mem() = Runtime.getRuntime().let { it.totalMemory() - it.freeMemory() }

val counter = MutableStateFlow(0)
val ram = MutableStateFlow(mem())

fun Application.configureRouting() {
    val config = environment.config

    fixedRateTimer(
        period = 1000,
    ) {
        ram.value = mem() / (1024 * 1024)
    }

    routing {
        get("/") {
            call.respond(
                MustacheContent(
                    "index.html",
                    mapOf(
                        "counter" to counter.value,
                        "ram" to ram.value,
                        "host" to config.host,
                        "port" to config.port,
                    )
                )
            )
        }

        webSocket("/") {
            send("${++counter.value},${ram.value}")

            val ctrJob = launch {
                counter.collect {
                    send("${counter.value},${ram.value}")
                }
            }

            val ramJob = launch {
            ram.collect {
                send("${counter.value},${ram.value}")
            }
        }

            closeReason.await()
            --counter.value

            ctrJob.cancel()
            ramJob.cancel()
        }
    }
}
