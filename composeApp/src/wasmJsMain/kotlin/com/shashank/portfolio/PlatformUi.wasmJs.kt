package com.shashank.portfolio

import kotlinx.browser.window

actual fun isTouchPlatform(): Boolean {
    val ua = window.navigator.userAgent
    return ua.contains("Mobi", ignoreCase = true) ||
        ua.contains("Android", ignoreCase = true) ||
        ua.contains("iPhone", ignoreCase = true) ||
        ua.contains("iPad", ignoreCase = true)
}
