package com.shashank.portfolio

import com.shashank.portfolio.util.encodeUriComponent
import kotlinx.browser.window

actual fun openUrl(url: String) {
    window.open(url, "_blank")
}

actual fun downloadFile(url: String, filename: String) {
    window.open(url, "_blank")
}

actual fun openEmail(to: String, subject: String, body: String) {
    // Gmail web compose — works reliably from Kotlin/Wasm in the browser
    val gmailUrl = buildString {
        append("https://mail.google.com/mail/?view=cm&fs=1")
        append("&to=").append(encodeUriComponent(to))
        append("&su=").append(encodeUriComponent(subject))
        append("&body=").append(encodeUriComponent(body))
    }
    window.open(gmailUrl, "_blank")
}
