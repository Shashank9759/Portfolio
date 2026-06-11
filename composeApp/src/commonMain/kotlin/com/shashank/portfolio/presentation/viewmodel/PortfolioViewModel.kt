package com.shashank.portfolio.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.shashank.portfolio.data.repository.PortfolioRepository
import com.shashank.portfolio.domain.model.PortfolioData
import com.shashank.portfolio.openEmail

/**
 * Simple ViewModel holding portfolio state and contact form data.
 * Contact form redirects to the user's email app via mailto: on submit.
 */
class PortfolioViewModel(
    private val repository: PortfolioRepository = PortfolioRepository(),
) {
    val portfolioData: PortfolioData = repository.getPortfolioData()

    var contactName by mutableStateOf("")
    var contactSubject by mutableStateOf("")
    var contactMessage by mutableStateOf("")
    var contactError by mutableStateOf<String?>(null)

    /**
     * Validates the form and opens the default email client (Gmail, Outlook, etc.)
     * with subject formatted as: "{user subject} - {user name}".
     */
    fun submitContactForm(recipientEmail: String) {
        contactError = null
        when {
            contactName.isBlank() -> contactError = "Please enter your name"
            contactSubject.isBlank() -> contactError = "Please enter a subject"
            contactMessage.isBlank() -> contactError = "Please enter a message"
            else -> {
                val emailSubject = "$contactSubject - $contactName"
                openEmail(
                    to = recipientEmail,
                    subject = emailSubject,
                    body = contactMessage.trim(),
                )
                contactName = ""
                contactSubject = ""
                contactMessage = ""
            }
        }
    }
}
