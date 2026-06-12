package com.shashank.portfolio

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.shashank.portfolio.presentation.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Shashank Ranjan | Portfolio",
        state = rememberWindowState(width = 1280.dp, height = 840.dp),
    ) {
        App()
    }
}
