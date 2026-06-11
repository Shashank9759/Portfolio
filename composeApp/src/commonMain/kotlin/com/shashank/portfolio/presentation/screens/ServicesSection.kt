package com.shashank.portfolio.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.domain.model.FreelanceService
import com.shashank.portfolio.presentation.animation.rememberScrollAnimation
import com.shashank.portfolio.presentation.animation.scrollReveal
import com.shashank.portfolio.presentation.components.*
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.ScreenSize
import com.shashank.portfolio.presentation.theme.Spacing
import com.shashank.portfolio.presentation.theme.screenSize

@Composable
fun ServicesSection(
    services: List<FreelanceService>,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    val animState = rememberScrollAnimation(isVisible)

    BoxWithConstraints(modifier = modifier.fillMaxWidth().padding(vertical = Spacing.section)) {
        val columns = when (screenSize(maxWidth)) {
            ScreenSize.Mobile -> 1
            ScreenSize.Tablet -> 2
            ScreenSize.Desktop -> 4
        }

        ContentContainer {
            Column(
                modifier = Modifier.scrollReveal(animState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SectionHeader(title = "Freelancing Services", centered = true)

                Spacer(modifier = Modifier.height(Spacing.xxl))

                val rows = services.chunked(columns)
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.lg)) {
                    rows.forEachIndexed { rowIndex, rowServices ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Spacing.lg),
                        ) {
                            rowServices.forEachIndexed { colIndex, service ->
                                val delay = (rowIndex * columns + colIndex) * 80
                                val cardAnim = rememberScrollAnimation(isVisible, delayMillis = delay)
                                ServiceCard(
                                    service = service,
                                    modifier = Modifier
                                        .weight(1f)
                                        .scrollReveal(cardAnim),
                                )
                            }
                            repeat(columns - rowServices.size) {
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
private fun ServiceCard(
    service: FreelanceService,
    modifier: Modifier = Modifier,
) {
    GlassCard(modifier = modifier.fillMaxWidth()) {
        Icon(
            imageVector = iconForKey(service.icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp),
        )
        Spacer(modifier = Modifier.height(Spacing.md))
        Text(
            text = service.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(Spacing.sm))
        Text(
            text = service.description,
            style = MaterialTheme.typography.bodyMedium,
            color = LocalExtendedColors.current.muted,
        )
    }
}
