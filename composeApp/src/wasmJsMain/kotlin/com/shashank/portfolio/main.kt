package com.shashank.portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.CanvasBasedWindow
import androidx.compose.ui.window.ComposeViewport
import com.shashank.portfolio.presentation.App
import kotlinx.browser.document

/**
 * Web entry point for the Compose Multiplatform portfolio.
 * Renders the app into the browser viewport via Kotlin/Wasm.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    CanvasBasedWindow("Portfolio") {
       App()
    }
}
