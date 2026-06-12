package com.shashank.portfolio

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import com.shashank.portfolio.presentation.App
import platform.AppKit.NSApp
import platform.AppKit.NSApplication

fun main() {
    NSApplication.sharedApplication()
    Window(
        title = "Shashank Ranjan | Portfolio",
        size = DpSize(width = 1280.dp, height = 840.dp),
    ) {
        App()
    }
    NSApp?.run()
}
