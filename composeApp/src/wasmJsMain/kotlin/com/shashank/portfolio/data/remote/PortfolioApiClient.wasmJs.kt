package com.shashank.portfolio.data.remote

import com.shashank.portfolio.domain.model.PortfolioData
import com.shashank.portfolio.domain.model.PortfolioResponse
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.json.Json
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.khronos.webgl.get
import org.w3c.fetch.Response

private val portfolioJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

/** Ktor API port — webpack dev server uses 8080, so API runs on 8090. */
actual fun portfolioApiBaseUrl(): String = "http://localhost:8090"

actual suspend fun fetchPortfolioRemote(): PortfolioData? {
    return try {
        val response: Response = window.fetch("${portfolioApiBaseUrl()}/api/portfolio").await()
        if (!response.ok) return null
        val buffer: ArrayBuffer = response.arrayBuffer().await()
        val int8 = Int8Array(buffer)
        val text = ByteArray(int8.length) { i -> int8[i].toByte() }.decodeToString()
        portfolioJson.decodeFromString<PortfolioResponse>(text).data
    } catch (_: Throwable) {
        null
    }
}
