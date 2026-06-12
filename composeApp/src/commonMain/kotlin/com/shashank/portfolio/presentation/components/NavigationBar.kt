package com.shashank.portfolio.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.presentation.navigation.PortfolioSection
import com.shashank.portfolio.presentation.theme.Layout
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.MonoFont
import com.shashank.portfolio.presentation.theme.ScreenSize
import com.shashank.portfolio.presentation.theme.Spacing
import com.shashank.portfolio.presentation.theme.screenSize

@Composable
fun ResponsiveNavigationBar(
    onNavigate: (PortfolioSection) -> Unit,
    scrollOffset: Int = 0,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier) {
        if (screenSize(maxWidth) == ScreenSize.Mobile) {
            MobileNavigationBar(onNavigate = onNavigate, scrollOffset = scrollOffset)
        } else {
            DesktopNavigationBar(
                onNavigate = onNavigate,
                scrollOffset = scrollOffset,
                compact = screenSize(maxWidth) == ScreenSize.Tablet,
            )
        }
    }
}

@Composable
private fun NavBarSurface(
    scrollOffset: Int,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val extended = LocalExtendedColors.current
    val bgAlpha by animateFloatAsState(
        targetValue = if (scrollOffset > 24) 0.96f else 0.88f,
        animationSpec = tween(300),
        label = "navBgAlpha",
    )
    val elevation by animateFloatAsState(
        targetValue = if (scrollOffset > 24) 8f else 2f,
        animationSpec = tween(300),
        label = "navElevation",
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        color = extended.glass.copy(alpha = bgAlpha),
        shadowElevation = elevation.dp,
        tonalElevation = 0.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, extended.border.copy(alpha = 0.45f)),
        ) {
            ContentContainer {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Layout.navVerticalPadding)
                        .height(Layout.navHeight),
                    verticalAlignment = Alignment.CenterVertically,
                    content = content,
                )
            }
        }
    }
}

@Composable
private fun DesktopNavigationBar(
    onNavigate: (PortfolioSection) -> Unit,
    scrollOffset: Int,
    compact: Boolean,
) {
    val sections = PortfolioSection.entries.filter { it != PortfolioSection.Home }
    val navScroll = rememberScrollState()

    NavBarSurface(scrollOffset = scrollOffset) {
        NavLogo(onNavigate, compact = compact)

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = Spacing.sm)
                .then(
                    if (compact) Modifier.horizontalScroll(navScroll)
                    else Modifier,
                ),
            horizontalArrangement = Arrangement.spacedBy(if (compact) 4.dp else 8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            sections.forEach { section ->
                AnimatedNavLink(
                    text = navLabel(section, compact),
                    onClick = { onNavigate(section) },
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        ) {
            ThemeModePicker(compact = compact)
            GradientButton(
                text = if (compact) "Hire" else "Hire Me",
                onClick = { onNavigate(PortfolioSection.Contact) },
                icon = Icons.Default.Email,
                modifier = Modifier.heightIn(min = 40.dp),
            )
        }
    }
}

private fun navLabel(section: PortfolioSection, compact: Boolean): String = when {
    compact && section == PortfolioSection.Testimonials -> "Reviews"
    compact && section == PortfolioSection.Experience -> "Work"
    else -> section.label
}

@Composable
private fun MobileNavigationBar(
    onNavigate: (PortfolioSection) -> Unit,
    scrollOffset: Int,
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        NavBarSurface(scrollOffset = scrollOffset) {
            Text(
                "Shashank Ranjan",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onNavigate(PortfolioSection.Contact) }) {
                    Icon(Icons.Default.Email, contentDescription = "Contact")
                }
                ThemeModePicker(compact = true)
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            }
        }
        if (expanded) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 6.dp,
            ) {
                Column(
                    Modifier
                        .padding(horizontal = Spacing.lg, vertical = Spacing.sm)
                        .border(1.dp, LocalExtendedColors.current.border),
                ) {
                    PortfolioSection.entries.forEach { section ->
                        AnimatedNavLink(
                            text = section.label,
                            onClick = { onNavigate(section); expanded = false },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NavLogo(onNavigate: (PortfolioSection) -> Unit, compact: Boolean) {
    val extended = LocalExtendedColors.current
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val color by animateColorAsState(
        if (hovered) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        tween(200),
        label = "logoColor",
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .hoverable(interactionSource)
            .clickable { onNavigate(PortfolioSection.Home) }
            .padding(end = if (compact) Spacing.sm else Spacing.md),
    ) {
        Text(
            text = "SR",
            style = MaterialTheme.typography.titleMedium.copy(fontFamily = MonoFont),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    1.dp,
                    if (hovered) MaterialTheme.colorScheme.primary else extended.border,
                    RoundedCornerShape(8.dp),
                )
                .background(
                    if (hovered) MaterialTheme.colorScheme.primary.copy(0.1f)
                    else extended.surfaceElevated,
                )
                .padding(horizontal = 10.dp, vertical = 6.dp),
        )
        if (!compact) {
            Spacer(modifier = Modifier.width(Spacing.md))
            Text(
                text = "Shashank Ranjan",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = color,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun AnimatedNavLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val extended = LocalExtendedColors.current
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val color by animateColorAsState(
        if (hovered) MaterialTheme.colorScheme.primary else extended.muted,
        tween(180),
        label = "navColor",
    )
    val bgAlpha by animateFloatAsState(
        if (hovered) 0.12f else 0f,
        tween(180),
        label = "navBg",
    )
    val underlineAlpha by animateFloatAsState(
        if (hovered) 1f else 0f,
        tween(200),
        label = "navUnderline",
    )

    // Isolated cell — no scale, no layout change; only color + background fade
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = bgAlpha))
            .hoverable(interactionSource)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = color,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                fontWeight = if (hovered) FontWeight.SemiBold else FontWeight.Medium,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .width(22.dp)
                    .height(2.dp)
                    .clip(RoundedCornerShape(1.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = underlineAlpha)),
            )
        }
    }
}
