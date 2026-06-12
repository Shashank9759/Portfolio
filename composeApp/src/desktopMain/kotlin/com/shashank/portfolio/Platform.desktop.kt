package com.shashank.portfolio

import com.shashank.portfolio.util.encodeUriComponent
import java.awt.Desktop
import java.net.URI

actual fun openUrl(url: String) {
    if (!Desktop.isDesktopSupported()) return
    val desktop = Desktop.getDesktop()
    if (!desktop.isSupported(Desktop.Action.BROWSE)) return
    desktop.browse(URI(url))
}

actual fun downloadFile(url: String, filename: String) {
    openUrl(url)
}

actual fun openEmail(to: String, subject: String, body: String) {
    if (!Desktop.isDesktopSupported()) return
    val desktop = Desktop.getDesktop()
    val mailto = buildString {
        append("mailto:")
        append(to)
        append("?subject=").append(encodeUriComponent(subject))
        append("&body=").append(encodeUriComponent(body))
    }
    if (desktop.isSupported(Desktop.Action.MAIL)) {
        desktop.mail(URI(mailto))
    } else {
        openUrl(mailto)
    }
}
