package com.shashank.portfolio.util

/** Brand logo URLs for social links and Topmate. */
object BrandLogoUrls {
    private const val CDN = "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons"

    fun urlFor(iconKey: String): String = when (iconKey) {
        "email" -> "$CDN/google/google-original.png"
        "resume" -> "$CDN/googledrive/googledrive-original.png"
        else -> "$CDN/google/google-original.png"
    }
}
