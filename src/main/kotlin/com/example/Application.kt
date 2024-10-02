package com.example

import com.example.plugins.*
import com.github.mustachejava.DefaultMustacheFactory
import io.ktor.server.mustache.Mustache
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(Mustache) {
        mustacheFactory = DefaultMustacheFactory("templates")
    }
    configureRouting()
}
