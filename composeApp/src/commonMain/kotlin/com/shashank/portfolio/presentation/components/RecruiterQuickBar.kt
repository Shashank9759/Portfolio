package com.shashank.portfolio.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.domain.model.PersonalInfo
import com.shashank.portfolio.openUrl
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.MonoFont
import com.shashank.portfolio.presentation.theme.ScreenSize
import com.shashank.portfolio.presentation.theme.Spacing
import com.shashank.portfolio.presentation.theme.screenSize

/** One-glance info strip for recruiters and freelance clients. */
@Composable
fun RecruiterQuickBar(
    personalInfo: PersonalInfo,
    modifier: Modifier = Modifier,
) {
    val extended = LocalExtendedColors.current

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val isMobile = screenSize(maxWidth) == ScreenSize.Mobile

        if (isMobile) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(extended.surfaceElevated.copy(alpha = 0.9f))
                    .border(1.dp, extended.border, RoundedCornerShape(14.dp))
                    .padding(Spacing.md),
                verticalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                QuickInfoItem(
                    icon = Icons.Default.Work,
                    text = "Open to freelance & full-time",
                    onClick = null,
                )
                QuickInfoItem(
                    icon = Icons.Default.Email,
                    text = personalInfo.email,
                    onClick = { openUrl("mailto:${personalInfo.email}") },
                )
                QuickInfoItem(icon = Icons.Default.LocationOn, text = personalInfo.location, onClick = null)
                QuickInfoItem(
                    icon = Icons.Default.Link,
                    text = "LinkedIn Profile",
                    onClick = { openUrl(personalInfo.linkedIn) },
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(extended.surfaceElevated.copy(alpha = 0.9f))
                    .border(1.dp, extended.border, RoundedCornerShape(14.dp))
                    .padding(horizontal = Spacing.lg, vertical = Spacing.md),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                QuickInfoItem(
                    icon = Icons.Default.Work,
                    text = "Open to freelance & full-time",
                    onClick = null,
                    compact = true,
                )
                QuickInfoItem(
                    icon = Icons.Default.Email,
                    text = personalInfo.email,
                    onClick = { openUrl("mailto:${personalInfo.email}") },
                )
                QuickInfoItem(
                    icon = Icons.Default.LocationOn,
                    text = personalInfo.location,
                    onClick = null,
                    compact = true,
                )
                QuickInfoItem(
                    icon = Icons.Default.Link,
                    text = "LinkedIn",
                    onClick = { openUrl(personalInfo.linkedIn) },
                    compact = true,
                )
                QuickInfoItem(
                    icon = Icons.Default.Code,
                    text = "GitHub",
                    onClick = { openUrl(personalInfo.github) },
                    compact = true,
                )
            }
        }
    }
}

@Composable
private fun QuickInfoItem(
    icon: ImageVector,
    text: String,
    onClick: (() -> Unit)?,
    compact: Boolean = false,
) {
    val extended = LocalExtendedColors.current
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val clickable = onClick != null

    Row(
        modifier = Modifier
            .then(
                if (clickable) {
                    Modifier
                        .graphicsLayer {
                            scaleX = if (hovered) 1.02f else 1f
                            scaleY = if (hovered) 1.02f else 1f
                        }
                        .hoverable(interactionSource)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(onClick = onClick!!)
                        .background(
                            if (hovered) MaterialTheme.colorScheme.primary.copy(0.1f)
                            else androidx.compose.ui.graphics.Color.Transparent,
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                } else {
                    Modifier
                },
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (hovered && clickable) MaterialTheme.colorScheme.primary else extended.accent,
            modifier = Modifier.size(if (compact) 16.dp else 18.dp),
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontFamily = if (text.contains("@")) MonoFont else MaterialTheme.typography.labelMedium.fontFamily,
            ),
            color = if (hovered && clickable) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            fontWeight = if (clickable) FontWeight.Medium else FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = if (compact) Modifier.widthIn(max = 200.dp) else Modifier,
        )
    }
}
