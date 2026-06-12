package com.shashank.portfolio.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.LocalResponsiveConfig
import com.shashank.portfolio.presentation.theme.MonoFont
import com.shashank.portfolio.presentation.theme.ScreenSize
import com.shashank.portfolio.presentation.theme.Spacing

/** Persistent contact shortcut for recruiters and freelance clients. */
@Composable
fun FloatingContactFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val extended = LocalExtendedColors.current
    val responsive = LocalResponsiveConfig.current
    val compact = responsive.screen == ScreenSize.Mobile
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()

    val infinite = rememberInfiniteTransition(label = "fabPulse")
    val pulse by infinite.animateFloat(
        initialValue = 1f,
        targetValue = if (compact) 1.03f else 1.06f,
        animationSpec = infiniteRepeatable(tween(1800, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "fabPulseScale",
    )

    val scale = if (hovered && responsive.enableHoverEffects) 1.1f else pulse

    Row(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(if (hovered) 16.dp else 10.dp, CircleShape, ambientColor = Color.Black.copy(0.3f))
            .clip(CircleShape)
            .background(extended.gradientBrush)
            .then(if (responsive.enableHoverEffects) Modifier.hoverable(interactionSource) else Modifier)
            .clickable(onClick = onClick)
            .padding(
                horizontal = if (compact) 16.dp else Spacing.lg,
                vertical = if (compact) 16.dp else 14.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        Icon(
            Icons.Default.Email,
            contentDescription = "Contact",
            tint = Color.White,
            modifier = Modifier.size(if (compact) 22.dp else 20.dp),
        )
        if (!compact) {
            Text(
                text = "Let's Talk",
                style = MaterialTheme.typography.labelLarge.copy(fontFamily = MonoFont),
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}
