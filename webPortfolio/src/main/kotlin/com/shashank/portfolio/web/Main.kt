package com.shashank.portfolio.web

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private const val PORT = 3000

fun main() {
    val distDir = SiteGenerator.generate()
    val url = "http://localhost:$PORT"

    println()
    println("============================================================")
    println("  SERVER IS RUNNING (Gradle 92% is NORMAL — not stuck)")
    println("  → $url")
    println("  → Files: ${distDir.absolutePath}")
    println("  → Stop: press Ctrl+C in this terminal")
    println("============================================================")
    println()

    openBrowser(url)

    embeddedServer(Netty, port = PORT, host = "127.0.0.1") {
        install(CORS) { anyHost() }
        routing {
            staticFiles("/", distDir) { default("index.html") }
            get("/") { call.respondRedirect("/index.html", permanent = false) }
        }
    }.start(wait = true)
}

private fun openBrowser(url: String) {
    val os = System.getProperty("os.name").lowercase()
    val cmd = when {
        os.contains("mac") -> arrayOf("open", url)
        os.contains("win") -> arrayOf("rundll32", "url.dll,FileProtocolHandler", url)
        else -> arrayOf("xdg-open", url)
    }
    runCatching { Runtime.getRuntime().exec(cmd) }
        .onFailure { println("Open this URL manually: $url") }
}
