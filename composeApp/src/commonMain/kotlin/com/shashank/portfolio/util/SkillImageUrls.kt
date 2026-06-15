package com.shashank.portfolio.util

/** Devicon CDN URLs — colorful tech logos for skill categories (PNG, cross-platform). */
object SkillImageUrls {
    private const val CDN = "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons"

    fun urlFor(iconKey: String): String = when (iconKey) {
        "android" -> "$CDN/android/android-original.png"
        "tv" -> "$CDN/android/android-plain.png"
        "kmp" -> "$CDN/kotlin/kotlin-original.png"
        "cmp" -> "$CDN/kotlin/kotlin-original.png"
        "ios" -> "$CDN/apple/apple-original.png"
        "cross" -> "$CDN/flutter/flutter-original.png"
        "api" -> "$CDN/firebase/firebase-original.png"
        "tools", "support" -> "$CDN/git/git-original.png"
        "ui" -> "$CDN/figma/figma-original.png"
        "perf" -> "$CDN/androidstudio/androidstudio-original.png"
        "ai" -> "$CDN/tensorflow/tensorflow-original.png"
        else -> "$CDN/kotlin/kotlin-original.png"
    }
}
