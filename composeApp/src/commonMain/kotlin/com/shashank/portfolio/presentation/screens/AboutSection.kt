package com.shashank.portfolio.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.domain.model.Education
import com.shashank.portfolio.presentation.animation.rememberScrollAnimation
import com.shashank.portfolio.presentation.animation.scrollReveal
import com.shashank.portfolio.presentation.components.BulletItem
import com.shashank.portfolio.presentation.components.ContentContainer
import com.shashank.portfolio.presentation.components.GlassCard
import com.shashank.portfolio.presentation.components.SectionHeader
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.ScreenSize
import com.shashank.portfolio.presentation.theme.Spacing
import com.shashank.portfolio.presentation.theme.responsiveSectionPadding
import com.shashank.portfolio.presentation.theme.screenSize

@Composable
fun AboutSection(
    summary: String,
    highlights: List<String>,
    education: List<Education>,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    val animState = rememberScrollAnimation(isVisible)
    val extendedColors = LocalExtendedColors.current

    BoxWithConstraints(modifier = modifier.fillMaxWidth().responsiveSectionPadding()) {
        val isMobile = screenSize(maxWidth) == ScreenSize.Mobile

        ContentContainer {
            Column(
                modifier = Modifier.scrollReveal(animState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SectionHeader(title = "About Me", centered = true)

                Spacer(modifier = Modifier.height(Spacing.xxl))

                if (isMobile) {
                    Column(verticalArrangement = Arrangement.spacedBy(Spacing.lg)) {
                        AboutContent(summary, highlights, education, extendedColors.muted)
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.xl),
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            AboutSummaryCard(summary, highlights)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            EducationCard(education)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AboutContent(
    summary: String,
    highlights: List<String>,
    education: List<Education>,
    mutedColor: androidx.compose.ui.graphics.Color,
) {
    AboutSummaryCard(summary, highlights)
    Spacer(modifier = Modifier.height(Spacing.lg))
    EducationCard(education)
}

@Composable
private fun AboutSummaryCard(summary: String, highlights: List<String>) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Professional Background",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(Spacing.md))
        Text(
            text = summary,
            style = MaterialTheme.typography.bodyLarge,
            color = LocalExtendedColors.current.muted,
        )
        Spacer(modifier = Modifier.height(Spacing.lg))
        Text(
            text = "Career Highlights",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(Spacing.md))
        highlights.forEach { highlight ->
            BulletItem(text = highlight)
        }
    }
}

@Composable
private fun EducationCard(education: List<Education>) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp),
            )
            Spacer(modifier = Modifier.width(Spacing.sm))
            Text(
                text = "Education",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Spacer(modifier = Modifier.height(Spacing.lg))
        education.forEach { edu ->
            Column(modifier = Modifier.padding(vertical = Spacing.sm)) {
                Text(
                    text = edu.degree,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = edu.institution,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "${edu.location} · ${edu.graduationDate}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalExtendedColors.current.muted,
                )
            }
            if (edu != education.last()) {
                HorizontalDivider(modifier = Modifier.padding(vertical = Spacing.sm))
            }
        }
    }
}
