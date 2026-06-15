package com.shashank.portfolio

import com.shashank.portfolio.util.buildGmailComposeUrl
import com.shashank.portfolio.util.buildMailtoUrl
import java.awt.Desktop
import java.net.URI

actual fun openUrl(url: String) {
    if (!Desktop.isDesktopSupported()) return
    val desktop = Desktop.getDesktop()
    if (!desktop.isSupported(Desktop.Action.BROWSE)) return
    runCatching { desktop.browse(URI(url)) }
}

actual fun downloadFile(url: String, filename: String) {
    openUrl(url)
}

actual fun openEmail(to: String, subject: String, body: String) {
    // Desktop.mail() and mailto: often fail silently on Windows/macOS without a default mail client.
    // Gmail web compose is the most reliable path for the contact form.
    val gmailUrl = buildGmailComposeUrl(to, subject, body)
    if (Desktop.isDesktopSupported()) {
        val desktop = Desktop.getDesktop()
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            runCatching {
                desktop.browse(URI(gmailUrl))
                return
            }
        }
        if (desktop.isSupported(Desktop.Action.MAIL)) {
            runCatching {
                desktop.mail(URI(buildMailtoUrl(to, subject, body)))
                return
            }
        }
    }
    openUrl(gmailUrl)
}
