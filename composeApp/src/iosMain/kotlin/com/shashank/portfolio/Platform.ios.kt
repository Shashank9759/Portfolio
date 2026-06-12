package com.shashank.portfolio

import com.shashank.portfolio.util.encodeUriComponent
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun openUrl(url: String) {
    val nsUrl = NSURL.URLWithString(url) ?: return
    UIApplication.sharedApplication.openURL(nsUrl)
}

actual fun downloadFile(url: String, filename: String) {
    openUrl(url)
}

actual fun openEmail(to: String, subject: String, body: String) {
    val mailto = buildString {
        append("mailto:")
        append(to)
        append("?subject=").append(encodeUriComponent(subject))
        append("&body=").append(encodeUriComponent(body))
    }
    openUrl(mailto)
}
