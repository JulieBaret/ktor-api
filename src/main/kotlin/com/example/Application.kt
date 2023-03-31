package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

//this module calls the following extension functions
fun Application.module() {
    //defines the routes
    configureRouting()
    //installs ContentNegociation and enables the JSON serializer
    configureSerialization()
}
