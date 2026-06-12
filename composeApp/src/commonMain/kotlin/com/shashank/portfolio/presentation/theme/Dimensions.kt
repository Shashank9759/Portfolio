package com.shashank.portfolio.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class ScreenSize { Mobile, Tablet, Desktop }

@Composable
fun screenSize(maxWidth: Dp): ScreenSize = when {
    maxWidth < 600.dp -> ScreenSize.Mobile
    maxWidth < 1024.dp -> ScreenSize.Tablet
    else -> ScreenSize.Desktop
}

object Spacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 16.dp
    val lg = 24.dp
    val xl = 32.dp
    val xxl = 48.dp
    val section = 80.dp
}

object Layout {
    val maxContentWidth = 1200.dp
    val navHeight = 56.dp
    val navVerticalPadding = 12.dp
    val navTotalHeight = navHeight + navVerticalPadding * 2
    val cardRadius = 16.dp
    val buttonRadius = 12.dp
}
