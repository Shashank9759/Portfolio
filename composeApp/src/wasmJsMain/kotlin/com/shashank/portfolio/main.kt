package com.shashank.portfolio

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.shashank.portfolio.presentation.App

/**
 * Web entry point for the Compose Multiplatform portfolio.
 * Renders the app into the browser viewport via Kotlin/Wasm.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(viewportContainerId = "ComposeTarget") {
        App()
    }
}
