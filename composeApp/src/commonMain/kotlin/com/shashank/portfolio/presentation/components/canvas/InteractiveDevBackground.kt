package com.shashank.portfolio.presentation.components.canvas

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import com.shashank.portfolio.presentation.animation.PhysicsDeviceState
import com.shashank.portfolio.presentation.animation.PhysicsDeviceType
import com.shashank.portfolio.presentation.animation.normalized
import com.shashank.portfolio.presentation.animation.rememberPhysicsDevices
import com.shashank.portfolio.presentation.theme.AndroidGreen
import com.shashank.portfolio.presentation.theme.BackgroundIntensity
import com.shashank.portfolio.presentation.theme.LocalThemePalette
import com.shashank.portfolio.presentation.theme.MonoFont
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

private data class VersionBadge(
    val text: String,
    val relX: Float,
    val relY: Float,
    val depth: Float,
    val phase: Float,
)

private val versionBadges = listOf(
    VersionBadge("Kotlin 2.1", 0.12f, 0.28f, 0.6f, 0f),
    VersionBadge("Compose MP 1.8", 0.72f, 0.22f, 0.45f, 1.2f),
    VersionBadge("Wasm", 0.58f, 0.85f, 0.7f, 2.4f),
    VersionBadge("KMP", 0.1f, 0.75f, 0.5f, 3.1f),
    VersionBadge("Skia Physics", 0.85f, 0.45f, 0.55f, 4.5f),
)

/**
 * Physics-driven developer device mockups — phones, tablets, and TVs that spring toward
 * the cursor with momentum, tilt, and repulsion. Rendered via Skia on Wasm.
 */
@Composable
fun InteractiveDevBackground(
    modifier: Modifier = Modifier,
    scrollOffset: Int = 0,
    pointerPosition: Offset = Offset.Zero,
    intensity: BackgroundIntensity = BackgroundIntensity.Full,
) {
    val showPhysics = intensity == BackgroundIntensity.Full
    val showEffects = intensity != BackgroundIntensity.Minimal
    val particleCount = when (intensity) {
        BackgroundIntensity.Full -> 80
        BackgroundIntensity.Light -> 32
        BackgroundIntensity.Minimal -> 0
    }
    val palette = LocalThemePalette.current
    val glowColor = palette.accentGlow
    val particleColor = palette.particleColor
    val bgColor = palette.colorScheme.background
    val parallax = scrollOffset * 0.06f
    val textMeasurer = rememberTextMeasurer()

    val infinite = rememberInfiniteTransition(label = "bgAnim")
    val pulse by infinite.animateFloat(
        0.92f, 1.08f,
        infiniteRepeatable(tween(3000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "pulse",
    )
    val particlePhase by infinite.animateFloat(
        0f, 360f,
        infiniteRepeatable(tween(22000, easing = LinearEasing), RepeatMode.Restart),
        label = "particles",
    )
    val auroraPhase by infinite.animateFloat(
        0f, 1f,
        infiniteRepeatable(tween(12000, easing = LinearEasing), RepeatMode.Restart),
        label = "aurora",
    )
    val orbitAngle by infinite.animateFloat(
        0f, 360f,
        infiniteRepeatable(tween(40000, easing = LinearEasing), RepeatMode.Restart),
        label = "orbit",
    )

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val canvasW = constraints.maxWidth.toFloat()
        val canvasH = constraints.maxHeight.toFloat()
        val norm = pointerPosition.normalized(canvasW, canvasH)
        val physicsDevices = rememberPhysicsDevices(
            canvasW, canvasH, pointerPosition, scrollOffset, enabled = showPhysics,
        )

        val hoveredId = remember(physicsDevices, pointerPosition, showPhysics) {
            if (!showPhysics || pointerPosition == Offset.Zero) null
            else physicsDevices.firstOrNull { it.bounds().contains(pointerPosition) }?.config?.id
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            if (showEffects) {
                drawAuroraWaves(glowColor, particleColor, auroraPhase, norm, w, h)
                drawParallaxGrid(glowColor.copy(0.08f), pulse, norm, parallax)
                if (intensity == BackgroundIntensity.Full) {
                    drawOrbitRings(glowColor.copy(0.07f), orbitAngle, norm, w, h)
                }
                if (particleCount > 0) {
                    drawParticleField(particleColor, particlePhase, pointerPosition, norm, w, h, particleCount)
                    drawParticleConnections(particleColor.copy(0.14f), particlePhase, pointerPosition, w, h)
                }
            } else {
                drawParallaxGrid(glowColor.copy(0.05f), pulse, norm, parallax)
            }

            // Physics devices — drawn before vignette so they stay visible at edges
            physicsDevices.forEach { device ->
                val isHovered = hoveredId == device.config.id
                val alpha = if (isHovered) 0.78f else 0.52f
                val scale = if (isHovered) 1.1f else 1f
                when (device.config.type) {
                    PhysicsDeviceType.PHONE -> drawPhysicsPhone(device, textMeasurer, glowColor, alpha, scale, isHovered)
                    PhysicsDeviceType.TV -> drawPhysicsTv(device, glowColor, alpha, scale, isHovered)
                    PhysicsDeviceType.TABLET -> drawPhysicsTablet(device, textMeasurer, glowColor, alpha, scale, isHovered)
                }
            }

            // Edge-only vignette — keeps center readable, devices visible on sides
            drawRect(
                brush = Brush.radialGradient(
                    listOf(Color.Transparent, bgColor.copy(0.35f), bgColor.copy(0.65f)),
                    center = Offset(w * 0.5f + norm.x * 30f, h * 0.35f - parallax + norm.y * 20f),
                    radius = w * 0.72f,
                ),
            )

            if (showEffects) {
                versionBadges.take(if (intensity == BackgroundIntensity.Light) 3 else versionBadges.size).forEach { badge ->
                    drawVersionBadge(badge, textMeasurer, glowColor, norm, parallax, auroraPhase, w, h)
                }
            }

            if (pointerPosition != Offset.Zero) {
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(glowColor.copy(0.22f), glowColor.copy(0.05f), Color.Transparent),
                        center = pointerPosition,
                        radius = 220f * pulse,
                    ),
                    radius = 220f * pulse,
                    center = pointerPosition,
                )
                drawCircle(color = glowColor.copy(0.35f), radius = 5f, center = pointerPosition)
            }
        }
    }
}

private fun DrawScope.drawPhysicsPhone(
    device: PhysicsDeviceState,
    textMeasurer: androidx.compose.ui.text.TextMeasurer,
    color: Color,
    alpha: Float,
    scale: Float,
    hovered: Boolean,
) {
    val pw = device.config.width * scale
    val ph = device.config.height * scale
    rotate(device.tilt, pivot = Offset(device.x, device.y)) {
        val topLeft = Offset(device.x - pw / 2f, device.y - ph / 2f)
        if (hovered) {
            drawRoundRect(
                brush = Brush.radialGradient(listOf(color.copy(0.4f), Color.Transparent), Offset(device.x, device.y), pw),
                topLeft = Offset(topLeft.x - 18f, topLeft.y - 18f),
                size = Size(pw + 36f, ph + 36f),
                cornerRadius = CornerRadius(22f),
            )
        }
        drawRoundRect(color.copy(alpha), topLeft, Size(pw, ph), CornerRadius(16f), Stroke(2.5f))
        val screen = Rect(topLeft.x + 7f, topLeft.y + 18f, topLeft.x + pw - 7f, topLeft.y + ph - 10f)
        drawRoundRect(color.copy(alpha * 0.55f), screen.topLeft, screen.size, CornerRadius(9f), Fill)
        drawCodeLines(screen, color, alpha, textMeasurer, device.config.label)
        drawCircle(color.copy(alpha * 1.3f), 9f, Offset(screen.right - 14f, screen.bottom - 14f))
    }
}

private fun DrawScope.drawPhysicsTablet(
    device: PhysicsDeviceState,
    textMeasurer: androidx.compose.ui.text.TextMeasurer,
    color: Color,
    alpha: Float,
    scale: Float,
    hovered: Boolean,
) {
    val pw = device.config.width * scale
    val ph = device.config.height * scale
    rotate(device.tilt * 0.7f, pivot = Offset(device.x, device.y)) {
        val topLeft = Offset(device.x - pw / 2f, device.y - ph / 2f)
        if (hovered) {
            drawRoundRect(
                brush = Brush.radialGradient(listOf(color.copy(0.35f), Color.Transparent), Offset(device.x, device.y), pw),
                topLeft = Offset(topLeft.x - 14f, topLeft.y - 14f),
                size = Size(pw + 28f, ph + 28f),
                cornerRadius = CornerRadius(14f),
            )
        }
        drawRoundRect(color.copy(alpha), topLeft, Size(pw, ph), CornerRadius(10f), Stroke(2f))
        val screen = Rect(topLeft.x + 6f, topLeft.y + 8f, topLeft.x + pw - 6f, topLeft.y + ph - 6f)
        drawRoundRect(AndroidGreen.copy(alpha * 0.25f), screen.topLeft, screen.size, CornerRadius(6f), Fill)
        drawCodeLines(screen, color, alpha, textMeasurer, device.config.label)
    }
}

private fun DrawScope.drawPhysicsTv(
    device: PhysicsDeviceState,
    color: Color,
    alpha: Float,
    scale: Float,
    hovered: Boolean,
) {
    val pw = device.config.width * scale
    val ph = device.config.height * scale
    rotate(device.tilt * 0.5f, pivot = Offset(device.x, device.y)) {
        val topLeft = Offset(device.x - pw / 2f, device.y - ph / 2f)
        if (hovered) {
            drawRoundRect(
                brush = Brush.radialGradient(listOf(color.copy(0.32f), Color.Transparent), Offset(device.x, device.y), pw),
                topLeft = Offset(topLeft.x - 12f, topLeft.y - 12f),
                size = Size(pw + 24f, ph + 24f),
                cornerRadius = CornerRadius(10f),
            )
        }
        drawRoundRect(color.copy(alpha), topLeft, Size(pw, ph), CornerRadius(7f), Stroke(2.5f))
        val screen = Rect(topLeft.x + 6f, topLeft.y + 6f, topLeft.x + pw - 6f, topLeft.y + ph - 10f)
        drawRoundRect(AndroidGreen.copy(alpha * 0.4f), screen.topLeft, screen.size, CornerRadius(4f), Fill)
        var cardX = screen.left + 8f
        repeat(5) {
            drawRoundRect(color.copy(alpha * 0.95f), Offset(cardX, screen.top + 8f), Size(32f, 18f), CornerRadius(2f), Stroke(1.2f))
            cardX += 36f
        }
        drawRoundRect(color.copy(alpha), Offset(device.x - pw * 0.08f, topLeft.y + ph), Size(pw * 0.16f, 5f), CornerRadius(1f))
    }
}

private fun DrawScope.drawCodeLines(
    screen: Rect,
    color: Color,
    alpha: Float,
    textMeasurer: androidx.compose.ui.text.TextMeasurer,
    label: String,
) {
    var barY = screen.top + 10f
    val barWidths = listOf(0.9f, 0.7f, 0.85f, 0.6f)
    barWidths.forEach { frac ->
        drawRoundRect(
            color.copy(alpha * 0.75f),
            Offset(screen.left + 6f, barY),
            Size((screen.width - 12f) * frac, 6f),
            CornerRadius(2f),
        )
        barY += 10f
    }
    if (label.isNotEmpty()) {
        val style = TextStyle(color = color.copy(alpha * 1.1f), fontSize = 9.sp, fontFamily = MonoFont)
        val layout = textMeasurer.measure(label, style)
        drawText(layout, topLeft = Offset(screen.left + 6f, screen.bottom - layout.size.height - 8f))
    }
}

private fun parallaxShift(norm: Offset, depth: Float, strength: Float): Offset =
    Offset(norm.x * strength * depth, norm.y * strength * depth)

private fun DrawScope.drawAuroraWaves(
    color1: Color, color2: Color, phase: Float, norm: Offset, w: Float, h: Float,
) {
    repeat(3) { i ->
        val path = Path().apply {
            val baseY = h * (0.12f + i * 0.1f) + norm.y * 24f
            moveTo(0f, baseY)
            var x = 0f
            while (x <= w) {
                val y = baseY + sin((x / w * 4f * PI + phase * 2f * PI + i).toFloat()) * (32f + i * 10f)
                lineTo(x, y)
                x += 20f
            }
            lineTo(w, h); lineTo(0f, h); close()
        }
        drawPath(
            path,
            brush = Brush.verticalGradient(
                listOf(color1.copy(0.05f + i * 0.02f), color2.copy(0.025f), Color.Transparent),
            ),
        )
    }
}

private fun DrawScope.drawParallaxGrid(color: Color, pulse: Float, norm: Offset, scroll: Float) {
    val spacing = 42f * pulse
    val ox = norm.x * 22f
    val oy = norm.y * 18f - scroll * 0.02f
    var x = ox % spacing
    while (x < size.width) {
        drawLine(color.copy(0.45f), Offset(x, 0f), Offset(x, size.height), 0.7f)
        x += spacing
    }
    var y = oy % spacing
    while (y < size.height) {
        drawLine(color.copy(0.45f), Offset(0f, y), Offset(size.width, y), 0.7f)
        y += spacing
    }
}

private fun DrawScope.drawOrbitRings(color: Color, angle: Float, norm: Offset, w: Float, h: Float) {
    val cx = w * 0.5f + norm.x * 40f
    val cy = h * 0.45f + norm.y * 30f
    repeat(4) { i ->
        rotate(angle * (0.25f + i * 0.18f), pivot = Offset(cx, cy)) {
            drawOval(
                color = color.copy(0.4f),
                topLeft = Offset(cx - 130f - i * 45f, cy - 65f - i * 22f),
                size = Size(260f + i * 90f, 130f + i * 44f),
                style = Stroke(1.2f),
            )
        }
    }
}

private fun DrawScope.drawParticleField(
    color: Color, phase: Float, pointer: Offset, norm: Offset, w: Float, h: Float, count: Int,
) {
    repeat(count) { i ->
        val angle = (phase + i * 91.7f) * (PI / 180f).toFloat()
        val layer = (i % 5) / 5f
        val cx = w * (0.04f + (i % 11) * 0.088f) + cos(angle) * (24f + layer * 20f) + norm.x * layer * 35f
        val cy = h * (0.06f + (i % 8) * 0.11f) + sin(angle) * (20f + layer * 16f) + norm.y * layer * 28f
        val dist = sqrt((cx - pointer.x) * (cx - pointer.x) + (cy - pointer.y) * (cy - pointer.y))
        val repel = if (dist < 160f && pointer != Offset.Zero) (160f - dist) / 160f else 0f
        val ox = if (dist > 1f && repel > 0f) (cx - pointer.x) / dist * repel * 70f else 0f
        val oy = if (dist > 1f && repel > 0f) (cy - pointer.y) / dist * repel * 70f else 0f
        drawCircle(color.copy(0.12f + repel * 0.4f), 2f + repel * 5f, Offset(cx + ox, cy + oy))
    }
}

private fun DrawScope.drawParticleConnections(
    color: Color, phase: Float, pointer: Offset, w: Float, h: Float,
) {
    val points = (0 until 14).map { i ->
        val angle = (phase + i * 25f) * (PI / 180f).toFloat()
        Offset(w * 0.5f + cos(angle) * w * 0.38f, h * 0.5f + sin(angle) * h * 0.3f)
    }
    for (i in points.indices) {
        for (j in i + 1 until points.size) {
            if ((i + j) % 3 == 0) drawLine(color, points[i], points[j], strokeWidth = 0.9f)
        }
    }
    if (pointer != Offset.Zero) {
        points.take(5).forEach { drawLine(color.copy(0.55f), pointer, it, strokeWidth = 0.7f) }
    }
}

private fun DrawScope.drawVersionBadge(
    badge: VersionBadge,
    textMeasurer: androidx.compose.ui.text.TextMeasurer,
    glow: Color, norm: Offset, scroll: Float, aurora: Float, w: Float, h: Float,
) {
    val shift = parallaxShift(norm, badge.depth, 55f)
    val floatY = sin((aurora * 2f * PI + badge.phase).toFloat()) * 14f
    val x = w * badge.relX + shift.x
    val y = h * badge.relY + shift.y + floatY - scroll * badge.depth * 0.08f
    val style = TextStyle(color = glow.copy(0.18f), fontSize = 12.sp, fontFamily = MonoFont, fontWeight = FontWeight.Medium)
    val layout = textMeasurer.measure(badge.text, style)
    val pad = 8f
    drawRoundRect(glow.copy(0.05f), Offset(x - pad, y - pad), Size(layout.size.width + pad * 2, layout.size.height + pad * 2), CornerRadius(6f))
    drawRoundRect(glow.copy(0.15f), Offset(x - pad, y - pad), Size(layout.size.width + pad * 2, layout.size.height + pad * 2), CornerRadius(6f), Stroke(1f))
    drawText(layout, topLeft = Offset(x, y))
}
