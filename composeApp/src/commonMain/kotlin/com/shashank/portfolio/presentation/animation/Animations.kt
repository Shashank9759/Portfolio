package com.shashank.portfolio.presentation.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt
import kotlinx.coroutines.delay

data class ScrollAnimationState(
    val alpha: Float,
    val offsetY: Float,
    val scale: Float = 1f,
    val rotationY: Float = 0f,
)

/** Fade + slide + 3D scale/rotate reveal on scroll. */
@Composable
fun rememberScrollAnimation(
    isVisible: Boolean,
    delayMillis: Int = 0,
    enable3D: Boolean = true,
): ScrollAnimationState {
    val alpha = remember { Animatable(0f) }
    val offsetY = remember { Animatable(50f) }
    val scale = remember { Animatable(if (enable3D) 0.88f else 1f) }
    val rotationY = remember { Animatable(if (enable3D) 8f else 0f) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(delayMillis.toLong())
            alpha.animateTo(1f, tween(700, easing = FastOutSlowInEasing))
            offsetY.animateTo(0f, tween(700, easing = FastOutSlowInEasing))
            if (enable3D) {
                scale.animateTo(1f, tween(800, easing = FastOutSlowInEasing))
                rotationY.animateTo(0f, tween(800, easing = FastOutSlowInEasing))
            }
        }
    }

    return ScrollAnimationState(alpha.value, offsetY.value, scale.value, rotationY.value)
}

fun Modifier.scrollReveal(state: ScrollAnimationState): Modifier = composed {
    this
        .graphicsLayer {
            this.alpha = state.alpha
            translationY = state.offsetY
            scaleX = state.scale
            scaleY = state.scale
            rotationY = state.rotationY
            cameraDistance = 14f * density
        }
}

@Composable
fun rememberAnimatedCounter(
    target: Int,
    isVisible: Boolean,
    durationMillis: Int = 1500,
): Int {
    var displayed by remember { mutableStateOf(0) }
    val animated by animateFloatAsState(
        targetValue = if (isVisible) target.toFloat() else 0f,
        animationSpec = tween(durationMillis, easing = FastOutSlowInEasing),
        label = "counter",
    )
    LaunchedEffect(animated) { displayed = animated.roundToInt() }
    return displayed
}

@Composable
fun rememberAnimatedProgress(
    target: Int,
    isVisible: Boolean,
    durationMillis: Int = 1200,
): Float {
    return animateFloatAsState(
        targetValue = if (isVisible) target / 100f else 0f,
        animationSpec = tween(durationMillis, easing = FastOutSlowInEasing),
        label = "skillProgress",
    ).value
}

@Composable
fun rememberFloatingOffset(range: Float = 14f): Float {
    val infiniteTransition = rememberInfiniteTransition(label = "float")
    return infiniteTransition.animateFloat(
        initialValue = -range / 2,
        targetValue = range / 2,
        animationSpec = infiniteRepeatable(tween(3500, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "floatOffset",
    ).value
}

@Composable
fun rememberPulseScale(isHovered: Boolean): Float {
    return animateFloatAsState(
        targetValue = if (isHovered) 1.06f else 1f,
        animationSpec = tween(250),
        label = "pulse",
    ).value
}

/** Continuous subtle 3D rocking animation for decorative elements. */
@Composable
fun remember3DRock(): Pair<Float, Float> {
    val infinite = rememberInfiniteTransition(label = "3dRock")
    val rotY by infinite.animateFloat(
        initialValue = -4f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(tween(4000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "rotY",
    )
    val rotX by infinite.animateFloat(
        initialValue = 2f,
        targetValue = -2f,
        animationSpec = infiniteRepeatable(tween(5000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "rotX",
    )
    return rotY to rotX
}

/** Shimmer sweep position 0..1 for progress bars and cards. */
@Composable
fun rememberShimmerPhase(): Float {
    val infinite = rememberInfiniteTransition(label = "shimmer")
    return infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2200, easing = LinearEasing), RepeatMode.Restart),
        label = "shimmer",
    ).value
}

/** Interactive 3D tilt on hover (mouse-parallax style). Must be called from a @Composable. */
@Composable
fun rememberHoverTilt(): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val rotY by animateFloatAsState(if (isHovered) 6f else 0f, tween(300), label = "tiltY")
    val rotX by animateFloatAsState(if (isHovered) -4f else 0f, tween(300), label = "tiltX")
    val scale by animateFloatAsState(if (isHovered) 1.03f else 1f, tween(300), label = "tiltScale")

    return Modifier
        .hoverable(interactionSource)
        .graphicsLayer {
            rotationY = rotY
            rotationX = rotX
            scaleX = scale
            scaleY = scale
            cameraDistance = 12f * density
        }
}

fun Modifier.hoverScale(scale: Float): Modifier = composed {
    this.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}

/** Gentle idle bob for cards, headers, and decorative elements. */
@Composable
fun Modifier.idleFloat(amplitude: Float = 6f, durationMs: Int = 3600): Modifier = composed {
    val infinite = rememberInfiniteTransition(label = "idleFloat")
    val offsetY by infinite.animateFloat(
        initialValue = -amplitude / 2f,
        targetValue = amplitude / 2f,
        animationSpec = infiniteRepeatable(tween(durationMs, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "idleY",
    )
    this.graphicsLayer { translationY = offsetY }
}

/** Mouse-reactive parallax shift for content layers. */
fun Modifier.mouseParallax(pointer: Offset, canvasWidth: Float, canvasHeight: Float, strength: Float = 12f): Modifier =
    composed {
        if (canvasWidth <= 0f || canvasHeight <= 0f) return@composed this
        val nx = (pointer.x / canvasWidth - 0.5f) * 2f
        val ny = (pointer.y / canvasHeight - 0.5f) * 2f
        this.graphicsLayer {
            translationX = nx * strength
            translationY = ny * strength * 0.6f
        }
    }

/** Stagger delay helper for list items. */
fun staggerDelay(index: Int, baseMs: Int = 80): Int = index * baseMs
