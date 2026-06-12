package com.shashank.portfolio.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.domain.model.PersonalInfo
import com.shashank.portfolio.domain.model.Stat
import com.shashank.portfolio.downloadFile
import com.shashank.portfolio.presentation.animation.ScrollAnimationState
import com.shashank.portfolio.presentation.animation.rememberAnimatedCounter
import com.shashank.portfolio.presentation.animation.rememberScrollAnimation
import com.shashank.portfolio.presentation.animation.scrollReveal
import com.shashank.portfolio.presentation.animation.staggerDelay
import com.shashank.portfolio.presentation.components.*
import com.shashank.portfolio.presentation.navigation.PortfolioSection
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.ui.platform.LocalDensity
import com.shashank.portfolio.presentation.theme.ExtendedColors
import com.shashank.portfolio.presentation.theme.Layout
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.LocalResponsiveConfig
import com.shashank.portfolio.presentation.theme.ScreenSize
import com.shashank.portfolio.presentation.theme.Spacing
import com.shashank.portfolio.presentation.theme.screenSize

private val heroTechStack = listOf(
    "Kotlin", "Jetpack Compose", "Android TV", "Leanback", "KMP", "CMP", "SwiftUI", "UIKit",
)

@Composable
fun HeroSection(
    personalInfo: PersonalInfo,
    stats: List<Stat>,
    isVisible: Boolean,
    onNavigate: (PortfolioSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    val extended = LocalExtendedColors.current
    val responsive = LocalResponsiveConfig.current
    val density = LocalDensity.current
    val statusBarTop = with(density) { WindowInsets.statusBars.getTop(density).toDp() }
    val animState = rememberScrollAnimation(isVisible, enable3D = responsive.enableHoverEffects)

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val isMobile = screenSize(maxWidth) == ScreenSize.Mobile

        Box(modifier = Modifier.fillMaxWidth()) {
            SectionContentBackdrop(modifier = Modifier.matchParentSize())

            ContentContainer {
                Column(
                    modifier = Modifier
                        .scrollReveal(animState)
                        .padding(
                            top = Layout.navTotalHeight + statusBarTop + responsive.heroTopPadding,
                            bottom = responsive.sectionSpacing,
                        ),
                ) {
                    RecruiterQuickBar(
                        personalInfo = personalInfo,
                        modifier = Modifier.padding(bottom = Spacing.xl),
                    )

                    if (isMobile) {
                        HeroAvatar(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = Spacing.lg),
                        )
                        HeroTextBlock(personalInfo, isVisible, onNavigate, extended, centered = true)
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(modifier = Modifier.weight(1f).padding(end = 48.dp)) {
                                HeroTextBlock(personalInfo, isVisible, onNavigate, extended)
                            }
                            HeroAvatar(modifier = Modifier.padding(start = Spacing.md))
                        }
                    }

                    HeroStatsRow(stats, isVisible, isMobile)
                }
            }
        }
    }
}

@Composable
private fun HeroTextBlock(
    personalInfo: PersonalInfo,
    isVisible: Boolean,
    onNavigate: (PortfolioSection) -> Unit,
    extended: ExtendedColors,
    centered: Boolean = false,
) {
    val align = if (centered) Alignment.CenterHorizontally else Alignment.Start

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = align,
    ) {
        StatusBadge(text = "Available for freelance & full-time")
        Spacer(modifier = Modifier.height(Spacing.lg))

        Text(
            text = personalInfo.name,
            style = MaterialTheme.typography.displayLarge.copy(brush = extended.nameGradientBrush),
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(Spacing.md))
        Text(
            text = personalInfo.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(Spacing.lg))
        Text(
            text = personalInfo.summary,
            style = MaterialTheme.typography.bodyLarge,
            color = extended.muted,
            modifier = Modifier.widthIn(max = 580.dp),
        )
        Spacer(modifier = Modifier.height(Spacing.xl))

        FlowLayoutPills(heroTechStack, isVisible, centered)

        Spacer(modifier = Modifier.height(Spacing.xxl))
        HeroCTAButtons(personalInfo, onNavigate, centered)
    }
}

@Composable
private fun FlowLayoutPills(techStack: List<String>, isVisible: Boolean, centered: Boolean) {
    val chunkSize = if (centered) 3 else 4
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm),
        horizontalAlignment = if (centered) Alignment.CenterHorizontally else Alignment.Start,
    ) {
        techStack.chunked(chunkSize).forEachIndexed { rowIdx, row ->
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                row.forEachIndexed { i, tech ->
                    val pillAnim = rememberScrollAnimation(isVisible, staggerDelay(rowIdx * chunkSize + i, 60), enable3D = false)
                    TechPill(text = tech, modifier = Modifier.scrollReveal(pillAnim))
                }
            }
        }
    }
}

@Composable
private fun HeroCTAButtons(
    personalInfo: PersonalInfo,
    onNavigate: (PortfolioSection) -> Unit,
    centered: Boolean,
) {
    if (centered) {
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.md), modifier = Modifier.fillMaxWidth()) {
            GradientButton("Hire Me", { onNavigate(PortfolioSection.Contact) }, Modifier.fillMaxWidth(), Icons.Default.Work)
            OutlineButton("View Resume", { downloadFile(personalInfo.resumeUrl, "Resume.pdf") }, Modifier.fillMaxWidth(), Icons.Default.Description)
            OutlineButton("Contact", { onNavigate(PortfolioSection.Contact) }, Modifier.fillMaxWidth(), Icons.Default.Email)
        }
    } else {
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
            GradientButton("Hire Me", { onNavigate(PortfolioSection.Contact) }, icon = Icons.Default.Work)
            OutlineButton("View Resume", { downloadFile(personalInfo.resumeUrl, "Resume.pdf") }, icon = Icons.Default.Description)
            OutlineButton("Contact", { onNavigate(PortfolioSection.Contact) }, icon = Icons.Default.Email)
        }
    }
}

@Composable
private fun HeroStatsRow(stats: List<Stat>, isVisible: Boolean, isMobile: Boolean) {
    val extended = LocalExtendedColors.current
    val columns = if (isMobile) 2 else 4

    Spacer(modifier = Modifier.height(Spacing.xl))
    HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = extended.border)
    Spacer(modifier = Modifier.height(Spacing.xl))

    stats.chunked(columns).forEachIndexed { rowIdx, rowStats ->
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            rowStats.forEachIndexed { i, stat ->
                val count = rememberAnimatedCounter(stat.value, isVisible)
                val cardAnim = rememberScrollAnimation(isVisible, staggerDelay(rowIdx * columns + i, 100))
                StatCard(
                    value = "$count${stat.suffix}",
                    label = stat.label,
                    showStarIcon = stat.showStarIcon,
                    modifier = Modifier.weight(1f).scrollReveal(cardAnim),
                )
            }
            repeat(columns - rowStats.size) { Spacer(modifier = Modifier.weight(1f)) }
        }
    }
}
