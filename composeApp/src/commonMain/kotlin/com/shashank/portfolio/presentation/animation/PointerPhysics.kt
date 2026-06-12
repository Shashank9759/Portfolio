package com.shashank.portfolio.presentation.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.launch

/** Smooth, spring-physics pointer tracking for parallax backgrounds. */
@Composable
fun rememberSmoothPointer(raw: Offset): Offset {
    val smoothX = remember { Animatable(raw.x) }
    val smoothY = remember { Animatable(raw.y) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(raw) {
        scope.launch {
            smoothX.animateTo(raw.x, spring(stiffness = Spring.StiffnessMediumLow, dampingRatio = 0.82f))
        }
        scope.launch {
            smoothY.animateTo(raw.y, spring(stiffness = Spring.StiffnessMediumLow, dampingRatio = 0.82f))
        }
    }

    return Offset(smoothX.value, smoothY.value)
}

/** Normalized pointer in -1..1 range from canvas center. */
fun Offset.normalized(canvasWidth: Float, canvasHeight: Float): Offset {
    if (canvasWidth <= 0f || canvasHeight <= 0f) return Offset.Zero
    return Offset(
        x = (x / canvasWidth - 0.5f) * 2f,
        y = (y / canvasHeight - 0.5f) * 2f,
    )
}
