package com.shashank.portfolio.presentation.components

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

/**
 * Tracks whether a composable is visible in the viewport for scroll-triggered animations.
 * Uses window bounds to determine visibility relative to the screen.
 */
class VisibilityState {
    var isVisible by mutableStateOf(false)
}

@Composable
fun rememberVisibilityState(): VisibilityState = remember { VisibilityState() }

fun Modifier.onVisibilityChanged(state: VisibilityState): Modifier = composed {
    val density = LocalDensity.current
    val threshold = with(density) { 100.dp.toPx() }

    this.onGloballyPositioned { coordinates ->
        val bounds = coordinates.boundsInWindow()
        val windowHeight = coordinates.size.height.toFloat() + bounds.top + bounds.bottom
        // Element is visible when its top is above the bottom of viewport
        state.isVisible = bounds.top < windowHeight && bounds.bottom > threshold
    }
}

/** Alternative visibility check based on scroll state and section index. */
@Composable
fun rememberSectionVisibility(
    scrollState: ScrollState,
    sectionOffset: Int,
    viewportHeight: Int = 800,
): Boolean {
    val scrollOffset = scrollState.value
    return remember(scrollOffset, sectionOffset) {
        scrollOffset + viewportHeight > sectionOffset - 200
    }
}
