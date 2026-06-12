package com.shashank.portfolio.presentation.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class ExtendedColors(
    val muted: Color,
    val accent: Color,
    val glass: Color,
    val border: Color,
    val surfaceElevated: Color,
    val gradientBrush: Brush,
    val nameGradientBrush: Brush,
    val cardGradient: Brush,
)

val LocalExtendedColors = staticCompositionLocalOf {
    paletteFor(PortfolioThemeMode.Midnight).extended
}

val LocalThemePalette = staticCompositionLocalOf {
    paletteFor(PortfolioThemeMode.Midnight)
}

/** Global theme mode — cycle or pick from ThemeModePicker. */
object ThemeState {
    var currentMode by mutableStateOf(PortfolioThemeMode.Midnight)

    fun cycle() {
        val modes = PortfolioThemeMode.entries
        val next = (modes.indexOf(currentMode) + 1) % modes.size
        currentMode = modes[next]
    }
}

@Composable
fun PortfolioTheme(
    mode: PortfolioThemeMode = ThemeState.currentMode,
    screen: ScreenSize = LocalResponsiveConfig.current.screen,
    content: @Composable () -> Unit,
) {
    val palette = paletteFor(mode)
    // Smooth cross-fade between theme switches
    val animatedBg by animateColorAsState(palette.colorScheme.background, tween(600), label = "bg")
    val animatedPrimary by animateColorAsState(palette.colorScheme.primary, tween(600), label = "pri")

    val scheme = palette.colorScheme.copy(
        background = animatedBg,
        primary = animatedPrimary,
    )

    CompositionLocalProvider(
        LocalExtendedColors provides palette.extended,
        LocalThemePalette provides palette,
    ) {
        MaterialTheme(
            colorScheme = scheme,
            typography = responsiveTypography(screen),
            content = content,
        )
    }
}
