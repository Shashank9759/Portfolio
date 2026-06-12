package com.shashank.portfolio.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.openUrl
import com.shashank.portfolio.presentation.animation.hoverScale
import com.shashank.portfolio.presentation.animation.idleFloat
import com.shashank.portfolio.presentation.animation.rememberHoverTilt
import com.shashank.portfolio.presentation.animation.rememberPulseScale
import com.shashank.portfolio.presentation.animation.rememberShimmerPhase
import com.shashank.portfolio.presentation.theme.Layout
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.MonoFont
import com.shashank.portfolio.presentation.components.canvas.AnimatedSectionUnderline
import com.shashank.portfolio.presentation.theme.Spacing

/** Elevated surface card — clean borders, no heavy glass effect. */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val extended = LocalExtendedColors.current
    val shape = RoundedCornerShape(Layout.cardRadius)
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val tiltModifier = rememberHoverTilt()

    Column(
        modifier = modifier
            .then(tiltModifier)
            .shadow(if (isHovered) 16.dp else 6.dp, shape, ambientColor = Color.Black.copy(alpha = 0.25f))
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
            .border(1.dp, extended.border, shape)
            .hoverable(interactionSource)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(Spacing.lg),
        content = content,
    )
}

/** Clean section header with title and accent underline. */
@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    centered: Boolean = false,
) {
    val extended = LocalExtendedColors.current
    val alignment = if (centered) Alignment.CenterHorizontally else Alignment.Start

    Column(
        modifier = modifier.idleFloat(amplitude = 4f, durationMs = 4200),
        horizontalAlignment = alignment,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = if (centered) TextAlign.Center else TextAlign.Start,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(Spacing.md))
        AnimatedSectionUnderline(width = 72f)
    }
}

/** Reliable bullet point — uses a drawn circle instead of unicode/icon fonts. */
@Composable
fun BulletItem(
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(vertical = Spacing.xs),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(6.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
        )
        Spacer(modifier = Modifier.width(Spacing.md))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
        )
    }
}

/** Monospace status badge (e.g. "Available for freelance"). */
@Composable
fun StatusBadge(
    text: String,
    modifier: Modifier = Modifier,
) {
    val extended = LocalExtendedColors.current
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, extended.border, RoundedCornerShape(20.dp))
            .background(extended.surfaceElevated)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(extended.accent),
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(fontFamily = MonoFont),
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

/** Tech stack pill shown in hero. */
@Composable
fun TechPill(text: String, modifier: Modifier = Modifier, index: Int = 0) {
    val extended = LocalExtendedColors.current
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val scale = rememberPulseScale(isHovered)

    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = if (isHovered) MaterialTheme.colorScheme.primary else extended.muted,
        modifier = modifier
            .hoverable(interactionSource)
            .hoverScale(scale)
            .clip(RoundedCornerShape(8.dp))
            .border(
                1.dp,
                if (isHovered) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else extended.border,
                RoundedCornerShape(8.dp),
            )
            .background(
                if (isHovered) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                else extended.surfaceElevated,
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
    )
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    filled: Boolean = true,
) {
    val extended = LocalExtendedColors.current
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val scale = rememberPulseScale(isHovered)
    val shape = RoundedCornerShape(Layout.buttonRadius)

    Box(
        modifier = modifier
            .hoverable(interactionSource)
            .hoverScale(scale)
            .clip(shape)
            .then(
                if (filled) {
                    Modifier.background(extended.gradientBrush)
                } else {
                    Modifier
                        .background(Color.Transparent)
                        .border(1.dp, extended.border, shape)
                },
            )
            .clickable(onClick = onClick)
            .padding(horizontal = Spacing.lg, vertical = 14.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (filled) Color.White else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(18.dp),
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(fontFamily = MonoFont),
                color = if (filled) Color.White else MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
fun OutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    GradientButton(text = text, onClick = onClick, modifier = modifier, icon = icon, filled = false)
}

@Composable
fun TechChip(text: String, modifier: Modifier = Modifier) {
    val extended = LocalExtendedColors.current
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp),
    )
}

@Composable
fun SkillBar(
    skillName: String,
    progress: Float,
    modifier: Modifier = Modifier,
) {
    val extended = LocalExtendedColors.current

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = skillName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.labelMedium,
                color = extended.muted,
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        val shimmer = rememberShimmerPhase()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(extended.surfaceElevated),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.6f + shimmer * 0.4f),
                            ),
                            startX = shimmer * 200f,
                            endX = shimmer * 200f + 300f,
                        ),
                    ),
            )
        }
    }
}

@Composable
fun StatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    showStarIcon: Boolean = false,
) {
    val extended = LocalExtendedColors.current
    GlassCard(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = value,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
            )
            if (showStarIcon) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star rating",
                    tint = Color(0xFFFBBF24),
                    modifier = Modifier.size(28.dp),
                )
            }
        }
        Spacer(modifier = Modifier.height(Spacing.xs))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = extended.muted,
        )
    }
}

@Composable
fun SocialIconButton(
    icon: ImageVector,
    label: String,
    url: String,
    modifier: Modifier = Modifier,
) {
    val extended = LocalExtendedColors.current
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    IconButton(
        onClick = { openUrl(url) },
        modifier = modifier
            .hoverable(interactionSource)
            .size(44.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, extended.border, RoundedCornerShape(10.dp))
            .background(
                if (isHovered) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                else extended.surfaceElevated,
            ),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isHovered) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
fun iconForKey(key: String): ImageVector = when (key) {
    "linkedin" -> Icons.Default.Link
    "github" -> Icons.Default.Code
    "email" -> Icons.Default.Email
    "resume" -> Icons.Default.Description
    "whatsapp" -> Icons.Default.Chat
    "android" -> Icons.Default.PhoneAndroid
    "tv" -> Icons.Default.Tv
    "ios", "swift" -> Icons.Default.PhoneIphone
    "kmp", "cmp", "cross" -> Icons.Default.Devices
    "api" -> Icons.Default.Cloud
    "ui" -> Icons.Default.Palette
    "perf" -> Icons.Default.Speed
    "support" -> Icons.Default.Support
    "tools" -> Icons.Default.Build
    else -> Icons.Default.Star
}

@Composable
fun ProjectPlaceholderImage(
    imageKey: String,
    modifier: Modifier = Modifier,
) {
    val colors = when (imageKey) {
        "cricradio" -> listOf(Color(0xFF1E3A5F), Color(0xFF2563EB))
        "codersaidhub" -> listOf(Color(0xFF312E81), Color(0xFF7C3AED))
        "rrbmustudies" -> listOf(Color(0xFF78350F), Color(0xFFF59E0B))
        "skinlens" -> listOf(Color(0xFF831843), Color(0xFFEC4899))
        "yaaddiary" -> listOf(Color(0xFF164E63), Color(0xFF06B6D4))
        "collegereg" -> listOf(Color(0xFF1E3A8A), Color(0xFF3B82F6))
        else -> listOf(Color(0xFF27272A), Color(0xFF3B82F6))
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.linearGradient(colors)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Default.PhoneAndroid,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.5f),
            modifier = Modifier.size(48.dp),
        )
    }
}

@Composable
fun ContentContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.lg),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier.widthIn(max = Layout.maxContentWidth).fillMaxWidth(),
            content = content,
        )
    }
}

/** Alternating section background wrapper. */
@Composable
fun SectionWrapper(
    modifier: Modifier = Modifier,
    alternate: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
) {
    val bg = if (alternate) {
        LocalExtendedColors.current.surfaceElevated.copy(alpha = 0.35f)
    } else {
        Color.Transparent
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(bg),
        content = content,
    )
}
