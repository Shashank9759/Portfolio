package com.shashank.portfolio.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.domain.model.PersonalInfo
import com.shashank.portfolio.presentation.components.ContentContainer
import com.shashank.portfolio.presentation.components.SocialLinkItem
import com.shashank.portfolio.presentation.components.SocialLinksGrid
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.Spacing

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

                SocialLinksGrid(
                    links = listOf(
                        SocialLinkItem("Resume", "resume", personalInfo.resumeUrl),
                        SocialLinkItem("LinkedIn", "linkedin", personalInfo.linkedIn),
                        SocialLinkItem("GitHub", "github", personalInfo.github),
                        SocialLinkItem("Topmate", "topmate", "https://topmate.io/shashank_ranjan10"),
                        SocialLinkItem("Email", "email", "mailto:${personalInfo.email}"),
                    ),
                )

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
