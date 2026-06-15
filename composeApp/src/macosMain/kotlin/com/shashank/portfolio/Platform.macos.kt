package com.shashank.portfolio

import com.shashank.portfolio.util.buildGmailComposeUrl
import platform.AppKit.NSWorkspace
import platform.Foundation.NSURL

actual fun openUrl(url: String) {
    val nsUrl = NSURL.URLWithString(url) ?: return
    NSWorkspace.sharedWorkspace.openURL(nsUrl)
}

actual fun downloadFile(url: String, filename: String) {
    openUrl(url)
}

actual fun openEmail(to: String, subject: String, body: String) {
    openUrl(buildGmailComposeUrl(to, subject, body))
}
