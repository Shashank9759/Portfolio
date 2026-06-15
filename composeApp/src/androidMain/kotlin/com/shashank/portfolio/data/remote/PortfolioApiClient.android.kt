package com.shashank.portfolio.data.remote

import com.shashank.portfolio.domain.model.PortfolioData
import com.shashank.portfolio.domain.model.PortfolioResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private val httpClient = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true; isLenient = true })
    }
}

actual fun portfolioApiBaseUrl(): String = "http://10.0.2.2:8090"

actual suspend fun fetchPortfolioRemote(): PortfolioData? = runCatching {
    httpClient.get("${portfolioApiBaseUrl()}/api/portfolio").body<PortfolioResponse>().data
}.getOrNull()
