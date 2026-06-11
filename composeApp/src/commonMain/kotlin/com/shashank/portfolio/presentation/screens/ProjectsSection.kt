package com.shashank.portfolio.presentation.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.domain.model.Project
import com.shashank.portfolio.openUrl
import com.shashank.portfolio.presentation.animation.rememberScrollAnimation
import com.shashank.portfolio.presentation.animation.scrollReveal
import com.shashank.portfolio.presentation.components.*
import com.shashank.portfolio.presentation.theme.Layout
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.ScreenSize
import com.shashank.portfolio.presentation.theme.Spacing
import com.shashank.portfolio.presentation.theme.screenSize

@Composable
fun ProjectsSection(
    projects: List<Project>,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    val animState = rememberScrollAnimation(isVisible)

    BoxWithConstraints(modifier = modifier.fillMaxWidth().padding(vertical = Spacing.section)) {
        val columns = when (screenSize(maxWidth)) {
            ScreenSize.Mobile -> 1
            ScreenSize.Tablet -> 2
            ScreenSize.Desktop -> 3
        }

        ContentContainer {
            Column(
                modifier = Modifier.scrollReveal(animState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SectionHeader(title = "Featured Projects", centered = true)

                Spacer(modifier = Modifier.height(Spacing.xxl))

                val rows = projects.chunked(columns)
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.lg)) {
                    rows.forEachIndexed { rowIndex, rowProjects ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Spacing.lg),
                        ) {
                            rowProjects.forEachIndexed { colIndex, project ->
                                val delay = (rowIndex * columns + colIndex) * 120
                                val cardAnim = rememberScrollAnimation(isVisible, delayMillis = delay)
                                ProjectCard(
                                    project = project,
                                    modifier = Modifier
                                        .weight(1f)
                                        .scrollReveal(cardAnim),
                                )
                            }
                            repeat(columns - rowProjects.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProjectCard(
    project: Project,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.02f else 1f,
        animationSpec = tween(200),
        label = "projectScale",
    )

    GlassCard(
        modifier = modifier
            .hoverable(interactionSource)
            .scale(scale),
    ) {
        ProjectPlaceholderImage(
            imageKey = project.imageKey,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
        )

        Spacer(modifier = Modifier.height(Spacing.md))

        Text(
            text = project.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        Text(
            text = project.description,
            style = MaterialTheme.typography.bodyMedium,
            color = LocalExtendedColors.current.muted,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )

        if (project.highlights.isNotEmpty()) {
            Spacer(modifier = Modifier.height(Spacing.sm))
            project.highlights.take(2).forEach { highlight ->
                Text(
                    text = "• $highlight",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacing.md))

        // Technology chips
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
            verticalArrangement = Arrangement.spacedBy(Spacing.xs),
        ) {
            project.technologies.take(4).forEach { tech ->
                TechChip(text = tech)
            }
        }

        Spacer(modifier = Modifier.height(Spacing.md))

        // Action links
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
            project.githubUrl?.let { url ->
                SmallActionButton(
                    text = "GitHub",
                    icon = Icons.Default.Code,
                    onClick = { openUrl(url) },
                )
            }
            project.liveUrl?.let { url ->
                SmallActionButton(
                    text = "Live",
                    icon = Icons.Default.OpenInNew,
                    onClick = { openUrl(url) },
                )
            }
            project.demoUrl?.let { url ->
                SmallActionButton(
                    text = "Demo",
                    icon = Icons.Default.PlayArrow,
                    onClick = { openUrl(url) },
                )
            }
        }
    }
}

@Composable
private fun SmallActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(Layout.buttonRadius),
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}

// FlowRow may not be available in all CMP versions - let me check
// Actually FlowRow is in foundation layout experimental or material3
// For CMP 1.8 it should be in androidx.compose.foundation.layout.FlowRow
