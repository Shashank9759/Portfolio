package com.shashank.portfolio.presentation.screens

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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.domain.model.PersonalInfo
import com.shashank.portfolio.openUrl
import com.shashank.portfolio.presentation.components.ContentContainer
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.ScreenSize
import com.shashank.portfolio.presentation.theme.Spacing
import com.shashank.portfolio.presentation.theme.screenSize

@Composable
fun FooterSection(
    personalInfo: PersonalInfo,
    modifier: Modifier = Modifier,
) {
    val extended = LocalExtendedColors.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .border(width = 1.dp, color = extended.border),
    ) {
        ContentContainer {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Spacing.xxl),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = personalInfo.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.height(Spacing.xs))
                Text(
                    text = personalInfo.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = extended.muted,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(Spacing.xl))

                Text(
                    text = "Connect with me",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(Spacing.lg))

                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    val isMobile = screenSize(maxWidth) == ScreenSize.Mobile
                    val links = listOf(
                        FooterLink("Resume", Icons.Default.Description, personalInfo.resumeUrl),
                        FooterLink("LinkedIn", Icons.Default.Link, personalInfo.linkedIn),
                        FooterLink("GitHub", Icons.Default.Code, personalInfo.github),
                        FooterLink("Email", Icons.Default.Email, "mailto:${personalInfo.email}"),
                    )

                    if (isMobile) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(Spacing.sm),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            links.forEach { link ->
                                FooterLinkCard(link = link, modifier = Modifier.fillMaxWidth())
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Spacing.md, Alignment.CenterHorizontally),
                        ) {
                            links.forEach { link ->
                                FooterLinkCard(link = link)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Spacing.xxl))

                HorizontalDivider(color = extended.border)

                Spacer(modifier = Modifier.height(Spacing.lg))

                Text(
                    text = "© 2026 ${personalInfo.name}. All rights reserved.",
                    style = MaterialTheme.typography.bodySmall,
                    color = extended.muted,
                )
                Spacer(modifier = Modifier.height(Spacing.xs))
                Text(
                    text = "Built with Compose Multiplatform · Kotlin/Wasm",
                    style = MaterialTheme.typography.labelMedium,
                    color = extended.muted.copy(alpha = 0.7f),
                )
            }
        }
    }
}

private data class FooterLink(
    val label: String,
    val icon: ImageVector,
    val url: String,
)

@Composable
private fun FooterLinkCard(
    link: FooterLink,
    modifier: Modifier = Modifier,
) {
    val extended = LocalExtendedColors.current
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val shape = RoundedCornerShape(12.dp)

    Row(
        modifier = modifier
            .clip(shape)
            .hoverable(interactionSource)
            .border(1.dp, if (isHovered) MaterialTheme.colorScheme.primary else extended.border, shape)
            .background(
                if (isHovered) MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                else extended.surfaceElevated,
            )
            .clickable { openUrl(link.url) }
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(
            imageVector = link.icon,
            contentDescription = link.label,
            tint = if (isHovered) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(20.dp),
        )
        Text(
            text = link.label,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = if (isHovered) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        )
    }
}
