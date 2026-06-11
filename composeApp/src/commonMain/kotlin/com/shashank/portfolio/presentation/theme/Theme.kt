package com.shashank.portfolio.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
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
    ExtendedColors(
        muted = MutedDark,
        accent = AccentDark,
        glass = GlassDark,
        border = BorderDark,
        surfaceElevated = SurfaceElevatedDark,
        gradientBrush = Brush.linearGradient(listOf(GradientStartDark, GradientEndDark)),
        nameGradientBrush = Brush.linearGradient(listOf(PrimaryDark, SecondaryDark)),
        cardGradient = Brush.linearGradient(listOf(SurfaceElevatedDark, SurfaceDark)),
    )
}

/** Defaults to dark theme — standard for professional developer portfolios. */
object ThemeState {
    var isDarkTheme by mutableStateOf(true)
}

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = Color.White,
    primaryContainer = PrimaryLight.copy(alpha = 0.1f),
    secondary = SecondaryLight,
    onSecondary = Color.White,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceElevatedLight,
    onSurfaceVariant = MutedLight,
    outline = BorderLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = Color.White,
    primaryContainer = PrimaryDark.copy(alpha = 0.12f),
    secondary = SecondaryDark,
    onSecondary = Color.White,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceElevatedDark,
    onSurfaceVariant = MutedDark,
    outline = BorderDark,
)

@Composable
fun PortfolioTheme(
    darkTheme: Boolean = ThemeState.isDarkTheme,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extendedColors = if (darkTheme) {
        ExtendedColors(
            muted = MutedDark,
            accent = AccentDark,
            glass = GlassDark,
            border = BorderDark,
            surfaceElevated = SurfaceElevatedDark,
            gradientBrush = Brush.linearGradient(listOf(GradientStartDark, GradientEndDark)),
            nameGradientBrush = Brush.linearGradient(listOf(Color(0xFFFAFAFA), PrimaryDark, SecondaryDark)),
            cardGradient = Brush.linearGradient(listOf(SurfaceElevatedDark.copy(alpha = 0.6f), SurfaceDark)),
        )
    } else {
        ExtendedColors(
            muted = MutedLight,
            accent = AccentLight,
            glass = GlassLight,
            border = BorderLight,
            surfaceElevated = SurfaceElevatedLight,
            gradientBrush = Brush.linearGradient(listOf(GradientStartLight, GradientEndLight)),
            nameGradientBrush = Brush.linearGradient(listOf(OnBackgroundLight, PrimaryLight, SecondaryLight)),
            cardGradient = Brush.linearGradient(listOf(SurfaceLight, SurfaceElevatedLight)),
        )
    }

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = PortfolioTypography,
            content = content,
        )
    }
}
