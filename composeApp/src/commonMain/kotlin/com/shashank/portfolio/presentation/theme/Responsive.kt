package com.shashank.portfolio.presentation.theme

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shashank.portfolio.isTouchPlatform

enum class BackgroundIntensity { Full, Light, Minimal }

data class ResponsiveConfig(
    val screen: ScreenSize,
    val horizontalPadding: Dp,
    val sectionSpacing: Dp,
    val heroTopPadding: Dp,
    val avatarSize: Dp,
    val backgroundIntensity: BackgroundIntensity,
    val enableHoverEffects: Boolean,
    val contentBottomPadding: Dp,
    val fabBottomPadding: Dp,
    val navTopPadding: Dp,
)

val LocalResponsiveConfig = staticCompositionLocalOf {
    ResponsiveConfig(
        screen = ScreenSize.Desktop,
        horizontalPadding = 24.dp,
        sectionSpacing = 80.dp,
        heroTopPadding = 32.dp,
        avatarSize = 260.dp,
        backgroundIntensity = BackgroundIntensity.Full,
        enableHoverEffects = true,
        contentBottomPadding = 24.dp,
        fabBottomPadding = 24.dp,
        navTopPadding = 0.dp,
    )
}

@Composable
fun ProvideResponsiveLayout(content: @Composable () -> Unit) {
    BoxWithConstraints {
        val touch = isTouchPlatform()
        val screen = screenSize(maxWidth)
        val config = remember(maxWidth, touch) {
            when (screen) {
                ScreenSize.Mobile -> ResponsiveConfig(
                    screen = screen,
                    horizontalPadding = 16.dp,
                    sectionSpacing = 48.dp,
                    heroTopPadding = 16.dp,
                    avatarSize = 180.dp,
                    backgroundIntensity = BackgroundIntensity.Minimal,
                    enableHoverEffects = false,
                    contentBottomPadding = 88.dp,
                    fabBottomPadding = 16.dp,
                    navTopPadding = if (touch) 0.dp else 0.dp,
                )
                ScreenSize.Tablet -> ResponsiveConfig(
                    screen = screen,
                    horizontalPadding = 20.dp,
                    sectionSpacing = 64.dp,
                    heroTopPadding = 24.dp,
                    avatarSize = 220.dp,
                    backgroundIntensity = if (touch) BackgroundIntensity.Light else BackgroundIntensity.Full,
                    enableHoverEffects = !touch,
                    contentBottomPadding = 72.dp,
                    fabBottomPadding = 20.dp,
                    navTopPadding = 0.dp,
                )
                ScreenSize.Desktop -> ResponsiveConfig(
                    screen = screen,
                    horizontalPadding = 24.dp,
                    sectionSpacing = 80.dp,
                    heroTopPadding = 32.dp,
                    avatarSize = 260.dp,
                    backgroundIntensity = BackgroundIntensity.Full,
                    enableHoverEffects = true,
                    contentBottomPadding = 32.dp,
                    fabBottomPadding = 24.dp,
                    navTopPadding = 0.dp,
                )
            }
        }
        CompositionLocalProvider(LocalResponsiveConfig provides config) {
            content()
        }
    }
}

fun responsiveTypography(screen: ScreenSize): Typography = when (screen) {
    ScreenSize.Mobile -> PortfolioTypography.copy(
        displayLarge = PortfolioTypography.displayLarge.copy(fontSize = 34.sp, lineHeight = 40.sp, letterSpacing = (-1).sp),
        displayMedium = PortfolioTypography.displayMedium.copy(fontSize = 28.sp, lineHeight = 34.sp),
        headlineLarge = PortfolioTypography.headlineLarge.copy(fontSize = 24.sp, lineHeight = 30.sp),
        headlineMedium = PortfolioTypography.headlineMedium.copy(fontSize = 18.sp, lineHeight = 26.sp),
        bodyLarge = PortfolioTypography.bodyLarge.copy(fontSize = 15.sp, lineHeight = 24.sp),
        titleLarge = PortfolioTypography.titleLarge.copy(fontSize = 17.sp),
    )
    ScreenSize.Tablet -> PortfolioTypography.copy(
        displayLarge = PortfolioTypography.displayLarge.copy(fontSize = 46.sp, lineHeight = 52.sp),
        headlineLarge = PortfolioTypography.headlineLarge.copy(fontSize = 26.sp, lineHeight = 34.sp),
    )
    ScreenSize.Desktop -> PortfolioTypography
}

@Composable
fun Modifier.responsiveSectionPadding(): Modifier = composed {
    padding(vertical = LocalResponsiveConfig.current.sectionSpacing)
}
