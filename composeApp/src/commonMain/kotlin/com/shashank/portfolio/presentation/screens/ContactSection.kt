package com.shashank.portfolio.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.domain.model.PersonalInfo
import com.shashank.portfolio.domain.model.SocialLink
import com.shashank.portfolio.openUrl
import com.shashank.portfolio.presentation.animation.rememberScrollAnimation
import com.shashank.portfolio.presentation.animation.scrollReveal
import com.shashank.portfolio.presentation.components.*
import com.shashank.portfolio.presentation.theme.LocalExtendedColors
import com.shashank.portfolio.presentation.theme.ScreenSize
import com.shashank.portfolio.presentation.theme.Spacing
import com.shashank.portfolio.presentation.theme.screenSize
import com.shashank.portfolio.presentation.viewmodel.PortfolioViewModel

@Composable
fun ContactSection(
    personalInfo: PersonalInfo,
    socialLinks: List<SocialLink>,
    viewModel: PortfolioViewModel,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    val animState = rememberScrollAnimation(isVisible)

    BoxWithConstraints(modifier = modifier.fillMaxWidth().padding(vertical = Spacing.section)) {
        val isMobile = screenSize(maxWidth) == ScreenSize.Mobile

        ContentContainer {
            Column(
                modifier = Modifier.scrollReveal(animState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SectionHeader(title = "Get In Touch", centered = true)

                Spacer(modifier = Modifier.height(Spacing.xxl))

                if (isMobile) {
                    Column(verticalArrangement = Arrangement.spacedBy(Spacing.lg)) {
                        ContactInfoPanel(personalInfo, socialLinks)
                        ContactFormPanel(viewModel, personalInfo.email)
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.xl),
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            ContactInfoPanel(personalInfo, socialLinks)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            ContactFormPanel(viewModel, personalInfo.email)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactInfoPanel(
    personalInfo: PersonalInfo,
    socialLinks: List<SocialLink>,
) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Let's work together",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(Spacing.md))
        Text(
            text = "I'm available for freelance projects, full-time opportunities, and consulting. " +
                "Reach out and let's build something amazing together.",
            style = MaterialTheme.typography.bodyLarge,
            color = LocalExtendedColors.current.muted,
        )

        Spacer(modifier = Modifier.height(Spacing.lg))

        ContactDetailRow(Icons.Default.Email, personalInfo.email) {
            openUrl("mailto:${personalInfo.email}")
        }
        ContactDetailRow(Icons.Default.Phone, personalInfo.phone) {
            openUrl("tel:${personalInfo.phone}")
        }
        ContactDetailRow(Icons.Default.LocationOn, personalInfo.location, onClick = null)

        Spacer(modifier = Modifier.height(Spacing.lg))

        Text(
            text = "Connect with me",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(Spacing.md))
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
            socialLinks.forEach { link ->
                SocialIconButton(
                    icon = iconForKey(link.icon),
                    label = link.name,
                    url = link.url,
                )
            }
        }
    }
}

@Composable
private fun ContactDetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: (() -> Unit)?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp),
        )
        Spacer(modifier = Modifier.width(Spacing.md))
        if (onClick != null) {
            TextButton(onClick = onClick) {
                Text(text = text, style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun ContactFormPanel(
    viewModel: PortfolioViewModel,
    recipientEmail: String,
) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Send a Message",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(Spacing.sm))
        Text(
            text = "Fill in the details below. Clicking Send opens Gmail in a new tab with your " +
                "subject formatted as \"Subject - Your Name\" and message pre-filled.",
            style = MaterialTheme.typography.bodySmall,
            color = LocalExtendedColors.current.muted,
        )
        Spacer(modifier = Modifier.height(Spacing.lg))

        OutlinedTextField(
            value = viewModel.contactName,
            onValueChange = { viewModel.contactName = it },
            label = { Text("Your Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(Spacing.md))

        OutlinedTextField(
            value = viewModel.contactSubject,
            onValueChange = { viewModel.contactSubject = it },
            label = { Text("Subject") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("e.g. Freelance Project Inquiry") },
        )
        Spacer(modifier = Modifier.height(Spacing.md))

        OutlinedTextField(
            value = viewModel.contactMessage,
            onValueChange = { viewModel.contactMessage = it },
            label = { Text("Message") },
            modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp),
            minLines = 4,
        )

        if (viewModel.contactError != null) {
            Spacer(modifier = Modifier.height(Spacing.sm))
            Text(
                text = viewModel.contactError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.height(Spacing.lg))

        GradientButton(
            text = "Send via Email",
            icon = Icons.AutoMirrored.Filled.Send,
            onClick = { viewModel.submitContactForm(recipientEmail) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
