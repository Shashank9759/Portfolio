package com.shashank.portfolio.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.presentation.animation.rememberFloatingOffset
import com.shashank.portfolio.presentation.components.canvas.CanvasGlowRing
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.LocalResponsiveConfig
import com.shashank.portfolio.presentation.theme.MonoFont
import com.shashank.portfolio.presentation.theme.ScreenSize

@Composable
fun HeroAvatar(modifier: Modifier = Modifier) {
    val extended = LocalExtendedColors.current
    val responsive = LocalResponsiveConfig.current
    val size = responsive.avatarSize
    val innerRing = size * 0.92f
    val innerCircle = size * 0.77f
    val logoSize = if (responsive.screen == ScreenSize.Mobile) size * 0.19f else size * 0.22f
    val floatOffset = rememberFloatingOffset(12f)
    val floatY = if (responsive.enableHoverEffects) floatOffset else 0f
    val showGlow = responsive.enableHoverEffects

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center,
    ) {
        if (showGlow) {
            CanvasGlowRing(modifier = Modifier.matchParentSize())
        }

        Box(
            modifier = Modifier
                .size(innerRing)
                .graphicsLayer { translationY = floatY }
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    brush = Brush.sweepGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary,
                            extended.accent,
                            MaterialTheme.colorScheme.primary,
                        ),
                    ),
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(innerCircle)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.surface.copy(0.9f)),
                        ),
                    )
                    .border(1.dp, extended.border, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        HeroTechLogo(
                            iconKey = "android",
                            contentDescription = "Android Developer",
                            modifier = Modifier.size(logoSize),
                        )
                        HeroTechLogo(
                            iconKey = "kmp",
                            contentDescription = "Kotlin Multiplatform",
                            modifier = Modifier.size(logoSize),
                        )
                        HeroTechLogo(
                            iconKey = "ai",
                            contentDescription = "AI Developer",
                            modifier = Modifier.size(logoSize),
                        )
                    }
                    Text(
                        text = "Mobile · Multiplatform · AI",
                        style = MaterialTheme.typography.labelMedium.copy(fontFamily = MonoFont),
                        color = extended.muted,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }
    }
}
