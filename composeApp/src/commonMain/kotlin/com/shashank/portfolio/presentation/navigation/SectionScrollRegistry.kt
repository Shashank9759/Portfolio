package com.shashank.portfolio.presentation.navigation

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.onGloballyPositioned
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Tracks each section's Y offset inside the scrollable column and scrolls to the correct position.
 * Replaces hardcoded pixel offsets that break on different screen sizes.
 */
class SectionScrollRegistry {
    private val sectionOffsets = mutableStateMapOf<PortfolioSection, Float>()

    fun updateOffset(section: PortfolioSection, yPosition: Float) {
        sectionOffsets[section] = yPosition
    }

    fun scrollToSection(
        section: PortfolioSection,
        scrollState: ScrollState,
        coroutineScope: CoroutineScope,
        navBarOffsetPx: Int = 0,
    ) {
        val target = sectionOffsets[section]?.toInt() ?: 0
        val scrollTarget = (target - navBarOffsetPx).coerceAtLeast(0)
        coroutineScope.launch {
            val target = scrollTarget.coerceAtMost(scrollState.maxValue)
            // Smooth scroll animation (animateScrollTo not available on Wasm target)
            val start = scrollState.value
            val distance = target - start
            if (distance != 0) {
                val steps = 24
                repeat(steps) { step ->
                    val progress = (step + 1).toFloat() / steps
                    val eased = 1f - (1f - progress) * (1f - progress) // ease-out
                    scrollState.scrollTo((start + distance * eased).toInt())
                    kotlinx.coroutines.delay(12)
                }
                scrollState.scrollTo(target)
            }
        }
    }
}

@Composable
fun rememberSectionScrollRegistry(): SectionScrollRegistry = remember { SectionScrollRegistry() }

/** Attach to each section root to register its scroll position. */
fun Modifier.sectionAnchor(
    section: PortfolioSection,
    registry: SectionScrollRegistry,
): Modifier = composed {
    this.onGloballyPositioned { coordinates ->
        registry.updateOffset(section, coordinates.positionInParent().y)
    }
}
