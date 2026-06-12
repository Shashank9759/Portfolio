package com.shashank.portfolio.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.PortfolioThemeMode
import com.shashank.portfolio.presentation.theme.ThemeState

/**
 * Theme picker anchored as a dropdown — never expands inline inside the nav row.
 */
@Composable
fun ThemeModePicker(compact: Boolean = false, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val extended = LocalExtendedColors.current

    Box(modifier = modifier.wrapContentSize(Alignment.TopEnd)) {
        ThemeChipButton(
            label = if (compact) ThemeState.currentMode.shortLabel else ThemeState.currentMode.label,
            accentColor = ThemeState.currentMode.accentColor,
            selected = true,
            showChevron = true,
            onClick = { expanded = !expanded },
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .widthIn(min = 180.dp)
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, extended.border, RoundedCornerShape(12.dp)),
        ) {
            PortfolioThemeMode.entries.forEach { mode ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(mode.accentColor),
                            )
                            Text(
                                text = mode.label,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (mode == ThemeState.currentMode) FontWeight.SemiBold else FontWeight.Normal,
                            )
                        }
                    },
                    onClick = {
                        ThemeState.currentMode = mode
                        expanded = false
                    },
                    leadingIcon = if (mode == ThemeState.currentMode) {
                        {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                    } else {
                        null
                    },
                )
            }
        }
    }
}

@Composable
private fun ThemeChipButton(
    label: String,
    accentColor: androidx.compose.ui.graphics.Color,
    selected: Boolean,
    showChevron: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val extended = LocalExtendedColors.current
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val bg by animateColorAsState(
        when {
            selected -> MaterialTheme.colorScheme.primary.copy(0.15f)
            hovered -> MaterialTheme.colorScheme.primary.copy(0.08f)
            else -> extended.surfaceElevated
        },
        tween(200),
        label = "chipBg",
    )
    val borderColor by animateColorAsState(
        if (selected || hovered) MaterialTheme.colorScheme.primary else extended.border,
        tween(200),
        label = "chipBorder",
    )

    Row(
        modifier = modifier
            .graphicsLayer {
                scaleX = if (hovered) 1.02f else 1f
                scaleY = if (hovered) 1.02f else 1f
            }
            .hoverable(interactionSource)
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(accentColor),
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        )
        if (showChevron) {
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.size(18.dp),
            )
        }
    }
}
