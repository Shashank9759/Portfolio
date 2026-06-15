package com.shashank.portfolio.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.domain.model.FreelanceService
import com.shashank.portfolio.presentation.theme.Layout
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.Spacing
import kotlinx.coroutines.delay

@Composable
fun LiveDataBadge(modifier: Modifier = Modifier) {
    val infinite = rememberInfiniteTransition(label = "livePulse")
    val alpha by infinite.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "liveAlpha",
    )
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = alpha)),
        )
        Text(
            text = "Live data from API",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun ServiceStatsBar(
    serviceCount: Int,
    modifier: Modifier = Modifier,
) {
    val primary = MaterialTheme.colorScheme.primary
    val extended = LocalExtendedColors.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(primary.copy(alpha = 0.08f))
            .padding(horizontal = 20.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        StatPill(value = "$serviceCount", label = "Services")
        StatPill(value = "50+", label = "Projects")
        StatPill(value = "100K+", label = "Downloads")
        StatPill(value = "24h", label = "Response")
    }
}

@Composable
private fun StatPill(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = LocalExtendedColors.current.muted,
        )
    }
}

@Composable
fun ServiceFilterChips(
    services: List<FreelanceService>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scroll = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scroll),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        services.forEachIndexed { index, service ->
            val selected = index == selectedIndex
            val colors = serviceGradient(service.icon)
            FilterChip(
                selected = selected,
                onClick = { onSelect(index) },
                label = {
                    Text(
                        text = service.title,
                        maxLines = 1,
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                leadingIcon = {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(
                                if (selected) Color.White.copy(alpha = 0.2f)
                                else colors.first().copy(alpha = 0.25f),
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = iconForKey(service.icon),
                            contentDescription = null,
                            tint = if (selected) Color.White else colors.first(),
                            modifier = Modifier.size(14.dp),
                        )
                    }
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = colors.first(),
                    selectedLabelColor = Color.White,
                    selectedLeadingIconColor = Color.White,
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = selected,
                    borderColor = colors.first().copy(alpha = 0.4f),
                    selectedBorderColor = Color.Transparent,
                ),
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeaturedServicePanel(
    service: FreelanceService,
    modifier: Modifier = Modifier,
) {
    val colors = serviceGradient(service.icon)
    val extended = LocalExtendedColors.current

    GlassCard(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.linearGradient(colors)),
                contentAlignment = Alignment.Center,
            ) {
                TechLogoImage(
                    iconKey = service.icon,
                    contentDescription = service.title,
                    modifier = Modifier.size(52.dp),
                    fallbackTint = Color.White,
                )
            }
            Spacer(modifier = Modifier.width(Spacing.lg))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Featured Service",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = service.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                if (service.deliveryHint.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = service.deliveryHint,
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.first(),
                    )
                }
                Spacer(modifier = Modifier.height(Spacing.sm))
                Text(
                    text = service.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = extended.muted,
                )
                if (service.tags.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(Spacing.md))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        service.tags.forEach { tag ->
                            TechChip(text = tag)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceGridCard(
    service: FreelanceService,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = serviceGradient(service.icon)
    val extended = LocalExtendedColors.current
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) colors.first() else extended.border,
        animationSpec = tween(250),
        label = "border",
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Layout.cardRadius))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(Layout.cardRadius),
            )
            .clickable(onClick = onClick)
            .padding(Spacing.lg),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Brush.linearGradient(colors.map { it.copy(alpha = 0.85f) })),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = iconForKey(service.icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp),
                )
            }
            Spacer(modifier = Modifier.width(Spacing.md))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = service.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                if (service.deliveryHint.isNotBlank()) {
                    Text(
                        text = service.deliveryHint,
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.first(),
                    )
                }
            }
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = colors.first(),
                    modifier = Modifier.size(20.dp),
                )
            }
        }
        Spacer(modifier = Modifier.height(Spacing.sm))
        Text(
            text = service.description,
            style = MaterialTheme.typography.bodySmall,
            color = extended.muted,
            maxLines = 3,
        )
    }
}

/** Auto-cycles featured service when section is visible. */
@Composable
fun rememberSelectedServiceIndex(
    serviceCount: Int,
    isVisible: Boolean,
    intervalMs: Long = 5000L,
): Int {
    var index by remember { mutableIntStateOf(0) }

    LaunchedEffect(isVisible, serviceCount) {
        if (!isVisible || serviceCount <= 1) return@LaunchedEffect
        while (true) {
            delay(intervalMs)
            index = (index + 1) % serviceCount
        }
    }
    return index
}

private fun serviceGradient(iconKey: String): List<Color> = when (iconKey) {
    "android" -> listOf(Color(0xFF2D6A4F), Color(0xFF40916C))
    "tv" -> listOf(Color(0xFF4338CA), Color(0xFF6366F1))
    "kmp" -> listOf(Color(0xFF7F52FF), Color(0xFFE4572E))
    "cmp" -> listOf(Color(0xFF1976D2), Color(0xFF42A5F5))
    "ios" -> listOf(Color(0xFF3A3A3C), Color(0xFF007AFF))
    "cross" -> listOf(Color(0xFF0891B2), Color(0xFFF59E0B))
    "api" -> listOf(Color(0xFF0369A1), Color(0xFF38BDF8))
    "ui" -> listOf(Color(0xFF7C3AED), Color(0xFFEC4899))
    "perf" -> listOf(Color(0xFFEA580C), Color(0xFFFBBF24))
    "support" -> listOf(Color(0xFF059669), Color(0xFF34D399))
    "ai" -> listOf(Color(0xFF6D28D9), Color(0xFFA855F7))
    else -> listOf(Color(0xFF3B82F6), Color(0xFF60A5FA))
}
