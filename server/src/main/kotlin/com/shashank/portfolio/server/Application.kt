package com.shashank.portfolio.server

import com.shashank.portfolio.data.source.PortfolioDataSource
import com.shashank.portfolio.domain.model.PortfolioMeta
import com.shashank.portfolio.domain.model.PortfolioResponse
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, port = 8090, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            },
        )
    }
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Get)
    }

    routing {
        get("/api/health") {
            call.respond(mapOf("status" to "ok", "service" to "portfolio-api"))
        }

        get("/api/portfolio") {
            val response = PortfolioResponse(
                meta = PortfolioMeta(
                    version = 1,
                    updatedAtEpochMs = System.currentTimeMillis(),
                ),
                data = PortfolioDataSource.getPortfolioData(),
            )
            call.respond(response)
        }

        get("/api/services") {
            call.respond(PortfolioDataSource.getPortfolioData().freelanceServices)
        }
    }
}
