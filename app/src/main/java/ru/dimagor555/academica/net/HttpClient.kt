package ru.dimagor555.academica.net

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

val client = HttpClient(CIO) {
    install(Logging) {
        logger = Logger.ANDROID
        level = LogLevel.ALL
    }
    install(DefaultRequest) {
        url("http://10.0.2.2:8080/")
    }
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
        })
    }
}