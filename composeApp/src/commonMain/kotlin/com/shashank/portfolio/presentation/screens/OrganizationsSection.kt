package com.shashank.portfolio.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.domain.model.ClientOrganization
import com.shashank.portfolio.openUrl
import com.shashank.portfolio.presentation.animation.rememberScrollAnimation
import com.shashank.portfolio.presentation.animation.scrollReveal
import com.shashank.portfolio.presentation.components.*
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.ScreenSize
import com.shashank.portfolio.presentation.theme.Spacing
import com.shashank.portfolio.presentation.theme.responsiveSectionPadding
import com.shashank.portfolio.presentation.theme.screenSize

@Composable
fun OrganizationsSection(
    organizations: List<ClientOrganization>,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    val animState = rememberScrollAnimation(isVisible)

    BoxWithConstraints(modifier = modifier.fillMaxWidth().responsiveSectionPadding()) {
        val columns = when (screenSize(maxWidth)) {
            ScreenSize.Mobile -> 1
            ScreenSize.Tablet -> 2
            ScreenSize.Desktop -> 2
        }
        val isMobile = screenSize(maxWidth) == ScreenSize.Mobile

        ContentContainer {
            Column(
                modifier = Modifier.scrollReveal(animState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SectionHeader(title = "Helping Organisations Build Apps", centered = true)

                Spacer(modifier = Modifier.height(Spacing.sm))

                Text(
                    text = "Partnering with teams and institutions to ship production-ready mobile products.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalExtendedColors.current.muted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = Spacing.md),
                )

                Spacer(modifier = Modifier.height(Spacing.xxl))

                val rows = organizations.chunked(columns)
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.lg)) {
                    rows.forEachIndexed { rowIndex, rowOrgs ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Spacing.lg),
                        ) {
                            rowOrgs.forEachIndexed { colIndex, org ->
                                val delay = if (isMobile) 0 else (rowIndex * columns + colIndex) * 120
                                val cardAnim = rememberScrollAnimation(
                                    isVisible = isVisible,
                                    delayMillis = delay,
                                    enable3D = !isMobile,
                                )
                                OrganizationCard(
                                    organization = org,
                                    modifier = Modifier
                                        .weight(1f)
                                        .scrollReveal(cardAnim),
                                )
                            }
                            repeat(columns - rowOrgs.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OrganizationCard(
    organization: ClientOrganization,
    modifier: Modifier = Modifier,
) {
    val extendedColors = LocalExtendedColors.current

    GlassCard(modifier = modifier) {
        ProjectImage(
            imageKey = organization.imageKey,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
        )

        Spacer(modifier = Modifier.height(Spacing.md))

        Text(
            text = organization.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
        )

        Text(
            text = organization.appName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        Text(
            text = organization.description,
            style = MaterialTheme.typography.bodyMedium,
            color = extendedColors.muted,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )

        organization.link?.let { url ->
            Spacer(modifier = Modifier.height(Spacing.md))
            OutlinedButton(
                onClick = { openUrl(url) },
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("View app", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
