package com.shashank.portfolio.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.shashank.portfolio.presentation.theme.AndroidGreen

/**
 * Android-themed background confined to screen edges only.
 * A center vignette keeps decorations away from readable content (fixes overlap bugs).
 */
@Composable
fun AndroidDevBackground(
    modifier: Modifier = Modifier,
    scrollOffset: Int = 0,
) {
    val primary = MaterialTheme.colorScheme.primary
    val bgColor = MaterialTheme.colorScheme.background
    val parallax = scrollOffset * 0.08f

    Canvas(modifier = modifier.fillMaxSize()) {
        // Dark vignette — clears the center for text/content
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.Transparent,
                    bgColor.copy(alpha = 0.55f),
                    bgColor.copy(alpha = 0.92f),
                ),
                center = Offset(size.width * 0.45f, size.height * 0.25f - parallax),
                radius = size.width * 0.75f,
            ),
        )

        drawDotGrid(AndroidGreen.copy(alpha = 0.12f))

        // --- EDGE-ONLY device mockups (never in center content zone) ---

        // Top-right phone
        drawPhoneMockup(
            topLeft = Offset(size.width * 0.88f, 20f - parallax),
            width = 72f, height = 128f, alpha = 0.35f,
        )
        // Bottom-left phone
        drawPhoneMockup(
            topLeft = Offset(size.width * 0.01f, size.height * 0.72f + parallax),
            width = 60f, height = 108f, alpha = 0.3f,
        )
        // Bottom-right TV
        drawTvMockup(
            topLeft = Offset(size.width * 0.78f, size.height * 0.78f + parallax * 0.5f),
            width = 140f, height = 82f, alpha = 0.32f,
        )
        // Top-left TV (small)
        drawTvMockup(
            topLeft = Offset(size.width * 0.01f, 40f - parallax * 0.5f),
            width = 120f, height = 70f, alpha = 0.28f,
        )

        // Corner glow orbs — far from content
        drawCircle(
            color = AndroidGreen.copy(alpha = 0.06f),
            radius = 100f,
            center = Offset(size.width * 0.95f, size.height * 0.1f),
        )
        drawCircle(
            color = primary.copy(alpha = 0.05f),
            radius = 90f,
            center = Offset(size.width * 0.05f, size.height * 0.9f),
        )
    }
}

private fun DrawScope.drawPhoneMockup(topLeft: Offset, width: Float, height: Float, alpha: Float) {
    val frameColor = AndroidGreen.copy(alpha = alpha)
    val corner = 14f
    drawRoundRect(
        color = frameColor,
        topLeft = topLeft,
        size = Size(width, height),
        cornerRadius = CornerRadius(corner, corner),
        style = Stroke(width = 1.5f),
    )
    val screenPad = 6f
    val screenTop = Offset(topLeft.x + screenPad, topLeft.y + 14f)
    val screenSize = Size(width - screenPad * 2, height - 22f)
    drawRoundRect(color = frameColor.copy(alpha = alpha * 0.5f), topLeft = screenTop, size = screenSize, cornerRadius = CornerRadius(8f, 8f), style = Fill)
    var rowY = screenTop.y + 14f
    repeat(3) {
        drawRoundRect(
            color = frameColor.copy(alpha = alpha * 0.6f),
            topLeft = Offset(screenTop.x + 5f, rowY),
            size = Size(screenSize.width - 10f, 7f),
            cornerRadius = CornerRadius(2f, 2f),
        )
        rowY += 12f
    }
    drawCircle(color = frameColor, radius = 7f, center = Offset(topLeft.x + width - 14f, topLeft.y + height - 14f))
}

private fun DrawScope.drawTvMockup(topLeft: Offset, width: Float, height: Float, alpha: Float) {
    val color = Color(0xFF3B82F6).copy(alpha = alpha)
    drawRoundRect(color = color, topLeft = topLeft, size = Size(width, height), cornerRadius = CornerRadius(6f, 6f), style = Stroke(width = 1.5f))
    val pad = 5f
    val screenTop = Offset(topLeft.x + pad, topLeft.y + pad)
    val screenSize = Size(width - pad * 2, height - pad * 2 - 6f)
    drawRoundRect(color = color.copy(alpha = alpha * 0.4f), topLeft = screenTop, size = screenSize, cornerRadius = CornerRadius(3f, 3f))
    // Leanback row
    var cardX = screenTop.x + 6f
    repeat(3) {
        drawRoundRect(color = color.copy(alpha = alpha * 0.7f), topLeft = Offset(cardX, screenTop.y + 14f), size = Size(28f, 18f), cornerRadius = CornerRadius(2f, 2f), style = Stroke(1f))
        cardX += 32f
    }
    drawRoundRect(color = color, topLeft = Offset(topLeft.x + width * 0.38f, topLeft.y + height), size = Size(width * 0.24f, 4f), cornerRadius = CornerRadius(1f, 1f))
}

private fun DrawScope.drawDotGrid(dotColor: Color) {
    val spacing = 32f
    var x = 0f
    while (x < size.width) {
        var y = 0f
        while (y < size.height) {
            drawCircle(color = dotColor, radius = 1f, center = Offset(x, y))
            y += spacing
        }
        x += spacing
    }
}

@Composable
fun GlowOrb(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier = modifier) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(color.copy(alpha = 0.15f), Color.Transparent),
                radius = size.minDimension / 2,
            ),
            radius = size.minDimension / 2,
        )
    }
}

/** Semi-opaque backdrop so content never clashes with background art. */
@Composable
fun SectionContentBackdrop(modifier: Modifier = Modifier) {
    val bgColor = MaterialTheme.colorScheme.background
    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    bgColor.copy(alpha = 0.72f),
                    bgColor.copy(alpha = 0.82f),
                ),
            ),
        )
    }
}
