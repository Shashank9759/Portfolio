package com.shashank.portfolio.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Icon
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
import com.shashank.portfolio.presentation.animation.remember3DRock
import com.shashank.portfolio.presentation.animation.rememberFloatingOffset
import com.shashank.portfolio.presentation.animation.idleFloat
import com.shashank.portfolio.presentation.components.canvas.CanvasGlowRing
import com.shashank.portfolio.presentation.theme.AndroidGreen
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.LocalResponsiveConfig
import com.shashank.portfolio.presentation.theme.MonoFont

@Composable
fun HeroAvatar(modifier: Modifier = Modifier) {
    val extended = LocalExtendedColors.current
    val responsive = LocalResponsiveConfig.current
    val size = responsive.avatarSize
    val innerRing = size * 0.92f
    val innerCircle = size * 0.77f
    val iconSize = size * 0.28f
    val floatOffset = rememberFloatingOffset(18f)
    val floatY = if (responsive.enableHoverEffects) floatOffset else 0f
    val rock = remember3DRock()
    val rotY = if (responsive.enableHoverEffects) rock.first else 0f
    val rotX = if (responsive.enableHoverEffects) rock.second else 0f

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center,
    ) {
        CanvasGlowRing(modifier = Modifier.matchParentSize())

        Box(
            modifier = Modifier
                .size(innerRing)
                .graphicsLayer {
                    translationY = floatY
                    rotationY = rotY
                    rotationX = rotX
                    cameraDistance = 16f * density
                }
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    brush = Brush.sweepGradient(
                        listOf(AndroidGreen, MaterialTheme.colorScheme.primary, extended.accent, AndroidGreen),
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Android,
                        contentDescription = "Android Developer",
                        tint = AndroidGreen,
                        modifier = Modifier.size(iconSize),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Icon(Icons.Default.Tv, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                        Text(
                            text = "Mobile · TV · KMP",
                            style = MaterialTheme.typography.labelMedium.copy(fontFamily = MonoFont),
                            color = extended.muted,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
        }
    }
}
