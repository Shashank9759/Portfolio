package com.shashank.portfolio.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.presentation.animation.rememberShimmerPhase
import com.shashank.portfolio.presentation.theme.LocalResponsiveConfig

@Composable
fun SkillCategoryHeader(
    categoryName: String,
    iconKey: String,
    modifier: Modifier = Modifier,
) {
    val gradient = skillGradientFor(iconKey)
    val responsive = LocalResponsiveConfig.current
    val shimmerPhase = rememberShimmerPhase()
    val shimmer = if (responsive.enableHoverEffects) shimmerPhase else 0f
    val infinite = rememberInfiniteTransition(label = "skillGlow")
    val glowAlpha by infinite.animateFloat(
        initialValue = 0.15f,
        targetValue = if (responsive.enableHoverEffects) 0.35f else 0.2f,
        animationSpec = if (responsive.enableHoverEffects) {
            infiniteRepeatable(tween(2800, easing = FastOutSlowInEasing), RepeatMode.Reverse)
        } else {
            infiniteRepeatable(tween(1), RepeatMode.Restart)
        },
        label = "glow",
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(108.dp)
            .clip(RoundedCornerShape(14.dp)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(gradient)),
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = glowAlpha * shimmer),
                            Color.Transparent,
                        ),
                        startX = shimmer * 800f - 200f,
                        endX = shimmer * 800f + 200f,
                    ),
                ),
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center,
            ) {
                TechLogoImage(
                    iconKey = iconKey,
                    contentDescription = categoryName,
                    modifier = Modifier.size(44.dp),
                    loadRemote = responsive.enableHoverEffects,
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = categoryName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                Text(
                    text = skillTaglineFor(iconKey),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.8f),
                )
            }
        }
    }
}

private fun skillTaglineFor(key: String): String = when (key) {
    "android" -> "Jetpack Compose · Kotlin"
    "tv" -> "Leanback · Android TV"
    "kmp" -> "Shared logic · Multiplatform"
    "cmp" -> "Web · Mobile · Desktop UI"
    "ios" -> "SwiftUI · UIKit"
    "cross" -> "React Native · Flutter"
    "api" -> "REST · WebSockets · Firebase"
    "ai" -> "Gemini · OpenAI · TFLite · ML Kit"
    "tools" -> "Git · Gradle · CI/CD"
    else -> "Professional expertise"
}

private fun skillGradientFor(key: String): List<Color> = when (key) {
    "android" -> listOf(Color(0xFF1B4332), Color(0xFF2D6A4F), Color(0xFF40916C))
    "tv" -> listOf(Color(0xFF1E1B4B), Color(0xFF4338CA), Color(0xFF6366F1))
    "kmp" -> listOf(Color(0xFF2D1B69), Color(0xFF7F52FF), Color(0xFFE4572E))
    "cmp" -> listOf(Color(0xFF0D47A1), Color(0xFF1976D2), Color(0xFF42A5F5))
    "ios" -> listOf(Color(0xFF1C1C1E), Color(0xFF3A3A3C), Color(0xFF007AFF))
    "cross" -> listOf(Color(0xFF4C1D95), Color(0xFF0891B2), Color(0xFFF59E0B))
    "api" -> listOf(Color(0xFF0C4A6E), Color(0xFF0369A1), Color(0xFF38BDF8))
    "ai" -> listOf(Color(0xFF312E81), Color(0xFF7C3AED), Color(0xFFA855F7))
    "tools" -> listOf(Color(0xFF7C2D12), Color(0xFFEA580C), Color(0xFFFBBF24))
    else -> listOf(Color(0xFF1E3A8A), Color(0xFF3B82F6), Color(0xFF60A5FA))
}
