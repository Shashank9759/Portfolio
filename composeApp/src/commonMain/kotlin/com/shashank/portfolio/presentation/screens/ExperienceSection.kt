package com.shashank.portfolio.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.domain.model.Experience
import com.shashank.portfolio.openUrl
import com.shashank.portfolio.presentation.animation.rememberScrollAnimation
import com.shashank.portfolio.presentation.animation.scrollReveal
import com.shashank.portfolio.presentation.components.BulletItem
import com.shashank.portfolio.presentation.components.ContentContainer
import com.shashank.portfolio.presentation.components.GlassCard
import com.shashank.portfolio.presentation.components.SectionHeader
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.Spacing
import com.shashank.portfolio.presentation.theme.responsiveSectionPadding

@Composable
fun ExperienceSection(
    experiences: List<Experience>,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    val animState = rememberScrollAnimation(isVisible)

    ContentContainer(
        modifier = modifier.responsiveSectionPadding(),
    ) {
        Column(
            modifier = Modifier.scrollReveal(animState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SectionHeader(title = "Work Experience", centered = true)

            Spacer(modifier = Modifier.height(Spacing.xxl))

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.lg)) {
                experiences.forEachIndexed { index, experience ->
                    val cardAnim = rememberScrollAnimation(isVisible, delayMillis = index * 150)
                    ExperienceCard(
                        experience = experience,
                        modifier = Modifier.scrollReveal(cardAnim),
                    )
                }
            }
        }
    }
}

@Composable
private fun ExperienceCard(
    experience: Experience,
    modifier: Modifier = Modifier,
) {
    val extendedColors = LocalExtendedColors.current

    GlassCard(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = experience.role,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = experience.company,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "${experience.location} · ${experience.period}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = extendedColors.muted,
                )
            }
            if (experience.link != null) {
                IconButton(onClick = { openUrl(experience.link) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = "View link",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Spacing.md))

        experience.highlights.forEach { highlight ->
            BulletItem(text = highlight)
        }
    }
}
