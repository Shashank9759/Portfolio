package com.shashank.portfolio.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.domain.model.SkillCategory
import com.shashank.portfolio.presentation.animation.rememberAnimatedProgress
import com.shashank.portfolio.presentation.animation.rememberScrollAnimation
import com.shashank.portfolio.presentation.animation.scrollReveal
import com.shashank.portfolio.presentation.components.*
import com.shashank.portfolio.presentation.theme.ScreenSize
import com.shashank.portfolio.presentation.theme.Spacing
import com.shashank.portfolio.presentation.theme.screenSize

@Composable
fun SkillsSection(
    categories: List<SkillCategory>,
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
                SectionHeader(title = "Skills & Expertise", centered = true)

                Spacer(modifier = Modifier.height(Spacing.xxl))

                val rows = categories.chunked(columns)
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.lg)) {
                    rows.forEachIndexed { rowIndex, rowCategories ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Spacing.lg),
                        ) {
                            rowCategories.forEachIndexed { colIndex, category ->
                                val delay = (rowIndex * columns + colIndex) * 100
                                val cardAnim = rememberScrollAnimation(isVisible, delayMillis = delay)
                                SkillCategoryCard(
                                    category = category,
                                    isVisible = isVisible,
                                    modifier = Modifier
                                        .weight(1f)
                                        .scrollReveal(cardAnim),
                                )
                            }
                            repeat(columns - rowCategories.size) {
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
private fun SkillCategoryCard(
    category: SkillCategory,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    GlassCard(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = iconForKey(category.icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.width(Spacing.sm))
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Spacer(modifier = Modifier.height(Spacing.lg))
        category.skills.forEach { skill ->
            val progress = rememberAnimatedProgress(skill.proficiency, isVisible)
            SkillBar(
                skillName = skill.name,
                progress = progress,
                modifier = Modifier.padding(vertical = Spacing.xs),
            )
        }
    }
}
