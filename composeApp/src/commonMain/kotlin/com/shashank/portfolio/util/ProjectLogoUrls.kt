package com.shashank.portfolio.util

/** Remote project logo URLs when no bundled asset exists. */
object ProjectLogoUrls {
    private const val CDN = "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons"

    fun urlFor(imageKey: String): String? = when (imageKey) {
        "collegereg" -> "$CDN/firebase/firebase-original.png"
        "dhm3" -> "$CDN/tensorflow/tensorflow-original.png"
        else -> null
    }
}
