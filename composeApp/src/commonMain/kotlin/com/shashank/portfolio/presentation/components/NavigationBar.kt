package com.shashank.portfolio.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.presentation.navigation.PortfolioSection
import com.shashank.portfolio.presentation.theme.Layout
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.MonoFont
import com.shashank.portfolio.presentation.theme.ScreenSize
import com.shashank.portfolio.presentation.theme.Spacing
import com.shashank.portfolio.presentation.theme.ThemeState
import com.shashank.portfolio.presentation.theme.screenSize

@Composable
fun ResponsiveNavigationBar(
    onNavigate: (PortfolioSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier) {
        if (screenSize(maxWidth) == ScreenSize.Mobile) {
            MobileNavigationBar(onNavigate = onNavigate)
        } else {
            DesktopNavigationBar(onNavigate = onNavigate)
        }
    }
}

@Composable
private fun DesktopNavigationBar(
    onNavigate: (PortfolioSection) -> Unit,
) {
    val extended = LocalExtendedColors.current

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = extended.glass,
        shadowElevation = 0.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 0.dp, color = extended.border)
                .border(width = 1.dp, color = extended.border.copy(alpha = 0.5f)),
        ) {
            ContentContainer {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Layout.navHeight),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onNavigate(PortfolioSection.Home) },
                    ) {
                        Text(
                            text = "SR",
                            style = MaterialTheme.typography.titleMedium.copy(fontFamily = MonoFont),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, extended.border, RoundedCornerShape(8.dp))
                                .background(extended.surfaceElevated)
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                        )
                        Spacer(modifier = Modifier.width(Spacing.md))
                        Text(
                            text = "Shashank Ranjan",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        PortfolioSection.entries
                            .filter { it != PortfolioSection.Home }
                            .forEach { section ->
                                NavLink(text = section.label, onClick = { onNavigate(section) })
                            }

                        Spacer(modifier = Modifier.width(Spacing.sm))

                        IconButton(
                            onClick = { ThemeState.isDarkTheme = !ThemeState.isDarkTheme },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, extended.border, RoundedCornerShape(8.dp)),
                        ) {
                            Icon(
                                imageVector = if (ThemeState.isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = "Toggle theme",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MobileNavigationBar(onNavigate: (PortfolioSection) -> Unit) {
    val extended = LocalExtendedColors.current
    var expanded by remember { mutableStateOf(false) }

    Column {
        Surface(modifier = Modifier.fillMaxWidth(), color = extended.glass) {
            ContentContainer {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Layout.navHeight),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Shashank Ranjan",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Row {
                        IconButton(onClick = { ThemeState.isDarkTheme = !ThemeState.isDarkTheme }) {
                            Icon(
                                imageVector = if (ThemeState.isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = "Toggle theme",
                            )
                        }
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                }
            }
        }

        if (expanded) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
            ) {
                Column(
                    modifier = Modifier
                        .padding(Spacing.md)
                        .border(width = 1.dp, color = extended.border),
                ) {
                    PortfolioSection.entries.forEach { section ->
                        Text(
                            text = section.label,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onNavigate(section)
                                    expanded = false
                                }
                                .padding(vertical = Spacing.md),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NavLink(text: String, onClick: () -> Unit) {
    var hovered by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        if (hovered) MaterialTheme.colorScheme.primary else LocalExtendedColors.current.muted,
        label = "navColor",
    )

    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = color,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 8.dp),
    )
}
