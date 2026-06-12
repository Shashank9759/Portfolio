package com.shashank.portfolio.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.domain.model.Testimonial
import com.shashank.portfolio.presentation.animation.rememberScrollAnimation
import com.shashank.portfolio.presentation.animation.scrollReveal
import com.shashank.portfolio.presentation.components.*
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.ScreenSize
import com.shashank.portfolio.presentation.theme.Spacing
import com.shashank.portfolio.presentation.theme.responsiveSectionPadding
import com.shashank.portfolio.presentation.theme.screenSize

@Composable
fun TestimonialsSection(
    testimonials: List<Testimonial>,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    val animState = rememberScrollAnimation(isVisible)

    BoxWithConstraints(modifier = modifier.fillMaxWidth().responsiveSectionPadding()) {
        val columns = when (screenSize(maxWidth)) {
            ScreenSize.Mobile -> 1
            else -> 3
        }

        ContentContainer {
            Column(
                modifier = Modifier.scrollReveal(animState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SectionHeader(title = "Client Testimonials", centered = true)

                Spacer(modifier = Modifier.height(Spacing.md))

                Text(
                    text = "Feedback from collaborators, academic partners, and clients.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalExtendedColors.current.muted,
                )

                Spacer(modifier = Modifier.height(Spacing.xxl))

                val rows = testimonials.chunked(columns)
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.lg)) {
                    rows.forEachIndexed { rowIndex, rowTestimonials ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Spacing.lg),
                        ) {
                            rowTestimonials.forEachIndexed { colIndex, testimonial ->
                                val delay = (rowIndex * columns + colIndex) * 100
                                val cardAnim = rememberScrollAnimation(isVisible, delayMillis = delay)
                                TestimonialCard(
                                    testimonial = testimonial,
                                    modifier = Modifier
                                        .weight(1f)
                                        .scrollReveal(cardAnim),
                                )
                            }
                            repeat(columns - rowTestimonials.size) {
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
private fun TestimonialCard(
    testimonial: Testimonial,
    modifier: Modifier = Modifier,
) {
    GlassCard(modifier = modifier.fillMaxWidth()) {
        Icon(
            imageVector = Icons.Default.FormatQuote,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            modifier = Modifier.size(32.dp),
        )

        Spacer(modifier = Modifier.height(Spacing.md))

        Text(
            text = "\"${testimonial.content}\"",
            style = MaterialTheme.typography.bodyLarge,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(Spacing.lg))

        Row {
            repeat(testimonial.rating) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacing.md))

        Text(
            text = testimonial.clientName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = "${testimonial.role} · ${testimonial.company}",
            style = MaterialTheme.typography.bodyMedium,
            color = LocalExtendedColors.current.muted,
        )

        if (testimonial.isPlaceholder) {
            Spacer(modifier = Modifier.height(Spacing.sm))
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.primaryContainer,
            ) {
                Text(
                    text = "Placeholder",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                )
            }
        }
    }
}
