package com.shashank.portfolio.presentation.components.canvas

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.presentation.theme.LocalExtendedColors

/** Animated gradient underline drawn on Canvas — used under section headers. */
@Composable
fun AnimatedSectionUnderline(modifier: Modifier = Modifier, width: Float = 80f) {
    val extended = LocalExtendedColors.current
    val infinite = rememberInfiniteTransition(label = "underline")
    val phase by infinite.animateFloat(
        0f, 1f,
        infiniteRepeatable(tween(2000, easing = LinearEasing), RepeatMode.Reverse),
        label = "phase",
    )

    val primary = MaterialTheme.colorScheme.primary
    val secondary = MaterialTheme.colorScheme.secondary

    Canvas(modifier = modifier.width(width.dp).height(4.dp)) {
        val startX = phase * size.width * 0.3f
        drawLine(
            brush = Brush.horizontalGradient(
                listOf(primary.copy(0.3f), primary, secondary, primary.copy(0.3f)),
                startX = startX,
                endX = startX + size.width,
            ),
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = 3f,
            cap = StrokeCap.Round,
        )
    }
}

/** Pulsing glow ring — wraps hero avatar or cards. */
@Composable
fun CanvasGlowRing(modifier: Modifier = Modifier) {
    val primary = MaterialTheme.colorScheme.primary
    val infinite = rememberInfiniteTransition(label = "ring")
    val scale by infinite.animateFloat(
        0.95f, 1.08f,
        infiniteRepeatable(tween(2500, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "ringScale",
    )
    val alpha by infinite.animateFloat(
        0.2f, 0.5f,
        infiniteRepeatable(tween(2500, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "ringAlpha",
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val r = size.minDimension / 2 * scale
        drawCircle(
            brush = Brush.radialGradient(
                listOf(primary.copy(alpha), primary.copy(0f)),
                radius = r,
            ),
            radius = r,
            center = center,
            style = Stroke(width = 2f),
        )
    }
}
