package com.shashank.portfolio.presentation.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.domain.model.FreelanceService
import com.shashank.portfolio.presentation.animation.rememberScrollAnimation
import com.shashank.portfolio.presentation.animation.scrollReveal
import com.shashank.portfolio.presentation.components.*
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.ScreenSize
import com.shashank.portfolio.presentation.theme.Spacing
import com.shashank.portfolio.presentation.theme.responsiveSectionPadding
import com.shashank.portfolio.presentation.theme.screenSize

@Composable
fun ServicesSection(
    services: List<FreelanceService>,
    isVisible: Boolean,
    isLiveData: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val animState = rememberScrollAnimation(isVisible)
    var selectedIndex by remember { mutableIntStateOf(0) }
    var userPicked by remember { mutableStateOf(false) }

    BoxWithConstraints(modifier = modifier.fillMaxWidth().responsiveSectionPadding()) {
        val isMobile = screenSize(maxWidth) == ScreenSize.Mobile
        val autoIndex = rememberSelectedServiceIndex(
            serviceCount = services.size,
            isVisible = isVisible && !userPicked && !isMobile,
        )
        LaunchedEffect(autoIndex) {
            if (!userPicked) selectedIndex = autoIndex
        }

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
                SectionHeader(title = "Freelancing Services", centered = true)

                if (isLiveData) {
                    Spacer(modifier = Modifier.height(Spacing.sm))
                    LiveDataBadge()
                }

                Spacer(modifier = Modifier.height(Spacing.md))

                Text(
                    text = "Select a service to explore — the featured panel updates automatically.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalExtendedColors.current.muted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = Spacing.md),
                )

                Spacer(modifier = Modifier.height(Spacing.lg))

                ServiceStatsBar(serviceCount = services.size)

                Spacer(modifier = Modifier.height(Spacing.lg))

                ServiceFilterChips(
                    services = services,
                    selectedIndex = selectedIndex,
                    onSelect = {
                        userPicked = true
                        selectedIndex = it
                    },
                )

                Spacer(modifier = Modifier.height(Spacing.xl))

                AnimatedContent(
                    targetState = selectedIndex,
                    transitionSpec = {
                        fadeIn(tween(350)) + slideInVertically(tween(350)) { it / 4 } togetherWith
                            fadeOut(tween(250)) + slideOutVertically(tween(250)) { -it / 4 }
                    },
                    label = "featuredService",
                ) { index ->
                    FeaturedServicePanel(service = services[index])
                }

                Spacer(modifier = Modifier.height(Spacing.xxl))

                val rows = services.chunked(columns)
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                    rows.forEachIndexed { rowIndex, rowServices ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
                        ) {
                            rowServices.forEachIndexed { colIndex, service ->
                                val globalIndex = rowIndex * columns + colIndex
                                val delay = globalIndex * 80
                                val cardAnim = rememberScrollAnimation(isVisible, delayMillis = delay)
                                ServiceGridCard(
                                    service = service,
                                    isSelected = globalIndex == selectedIndex,
                                    onClick = {
                                        userPicked = true
                                        selectedIndex = globalIndex
                                    },
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
