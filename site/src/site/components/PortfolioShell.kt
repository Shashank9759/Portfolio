package com.shashank.portfolio.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Canvas
import org.jetbrains.compose.web.dom.Div

@Composable
fun PortfolioShell(content: @Composable () -> Unit) {
    Canvas(attrs = {
        classes("dev-bg-canvas")
        id("dev-bg")
    })
    Div(attrs = { classes("content-backdrop") }) {}
    Div(attrs = { classes("page-content") }) {
        content()
    }
}
