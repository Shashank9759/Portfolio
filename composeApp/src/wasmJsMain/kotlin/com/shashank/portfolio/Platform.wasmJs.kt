package com.shashank.portfolio

import com.shashank.portfolio.util.buildGmailComposeUrl
import kotlinx.browser.window

actual fun openUrl(url: String) {
    window.open(url, "_blank")
}

actual fun downloadFile(url: String, filename: String) {
    window.open(url, "_blank")
}

actual fun openEmail(to: String, subject: String, body: String) {
    window.open(buildGmailComposeUrl(to, subject, body), "_blank")
}
