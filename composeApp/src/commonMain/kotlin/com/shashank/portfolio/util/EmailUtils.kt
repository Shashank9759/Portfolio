package com.shashank.portfolio.util

/** Gmail web compose URL — reliable fallback when mailto: / Desktop.mail() is unavailable. */
fun buildGmailComposeUrl(to: String, subject: String, body: String): String = buildString {
    append("https://mail.google.com/mail/?view=cm&fs=1")
    append("&to=").append(encodeUriComponent(to))
    append("&su=").append(encodeUriComponent(subject))
    append("&body=").append(encodeUriComponent(body))
}

fun buildMailtoUrl(to: String, subject: String, body: String): String = buildString {
    append("mailto:")
    append(to)
    append("?subject=").append(encodeUriComponent(subject))
    append("&body=").append(encodeUriComponent(body))
}
