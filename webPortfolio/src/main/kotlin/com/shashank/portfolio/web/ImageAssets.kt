package com.shashank.portfolio.web

import java.io.File

private const val DEVICON_CDN = "https://cdn.jsdelivr.net/npm/devicon@2.16.0/icons"

private val bundledImages = mapOf(
    "cricradio" to "project_cricradio.png",
    "codersaidhub" to "project_codersaidhub.png",
    "rrbmustudies" to "project_rrbmustudies.png",
    "skinlens" to "project_skinlens.png",
    "yaaddiary" to "project_yaaddiary.png",
    "putatoe" to "org_putatoe.png",
)

private val remoteImages = mapOf(
    "collegereg" to "$DEVICON_CDN/firebase/firebase-original.svg",
    "dhm3" to "$DEVICON_CDN/tensorflow/tensorflow-original.svg",
)

private val socialIcons = mapOf(
    "resume" to "brand_resume.png",
    "linkedin" to "brand_linkedin.png",
    "github" to "brand_github.png",
    "topmate" to "brand_topmate.png",
    "email" to "brand_gmail.png",
)

private val heroLogos = listOf(
    "hero_android.png",
    "hero_kotlin.png",
    "hero_ai.png",
)

fun imageSrc(imageKey: String): String =
    bundledImages[imageKey]?.let { "assets/$it" }
        ?: remoteImages[imageKey]
        ?: "assets/project_cricradio.png"

fun socialIconSrc(iconKey: String): String? =
    socialIcons[iconKey]?.let { "assets/$it" }

fun heroLogoSrc(fileName: String): String = "assets/$fileName"

fun heroLogoFiles(): List<String> = heroLogos

fun copyBundledAssets(distAssets: File, sourceAssets: File) {
    distAssets.mkdirs()
    if (!sourceAssets.isDirectory) return
    sourceAssets.listFiles()?.filter { it.extension == "png" }?.forEach { file ->
        file.copyTo(distAssets.resolve(file.name), overwrite = true)
    }
}

fun skillGradient(iconKey: String): String = when (iconKey) {
    "android" -> "linear-gradient(135deg,#1B4332,#2D6A4F,#40916C)"
    "tv" -> "linear-gradient(135deg,#1E1B4B,#4338CA,#6366F1)"
    "kmp" -> "linear-gradient(135deg,#2D1B69,#7F52FF,#E4572E)"
    "cmp" -> "linear-gradient(135deg,#0D47A1,#1976D2,#42A5F5)"
    "ios" -> "linear-gradient(135deg,#1C1C1E,#3A3A3C,#007AFF)"
    "cross" -> "linear-gradient(135deg,#4C1D95,#0891B2,#F59E0B)"
    "api" -> "linear-gradient(135deg,#0C4A6E,#0369A1,#38BDF8)"
    "ai" -> "linear-gradient(135deg,#312E81,#7C3AED,#A855F7)"
    "tools" -> "linear-gradient(135deg,#7C2D12,#EA580C,#FBBF24)"
    else -> "linear-gradient(135deg,#1E3A8A,#3B82F6,#60A5FA)"
}

fun skillTagline(iconKey: String): String = when (iconKey) {
    "android" -> "Jetpack Compose · Kotlin"
    "tv" -> "Leanback · Android TV"
    "kmp" -> "Shared logic · Multiplatform"
    "cmp" -> "Web · Mobile · Desktop UI"
    "ios" -> "SwiftUI · UIKit"
    "cross" -> "React Native · Flutter"
    "api" -> "REST · WebSockets · Firebase"
    "ai" -> "Gemini · OpenAI · TFLite · ML Kit"
    "tools" -> "Git · Gradle · CI/CD"
    else -> "Professional expertise"
}

/** Devicon SVG URLs (gh/@latest returns 403 on jsdelivr — use pinned npm package). */
fun techIconUrl(iconKey: String): String = when (iconKey) {
    "android" -> "$DEVICON_CDN/android/android-original.svg"
    "tv" -> "$DEVICON_CDN/android/android-plain.svg"
    "kmp", "cmp" -> "$DEVICON_CDN/kotlin/kotlin-original.svg"
    "ios" -> "$DEVICON_CDN/apple/apple-original.svg"
    "cross" -> "$DEVICON_CDN/flutter/flutter-original.svg"
    "api" -> "$DEVICON_CDN/firebase/firebase-original.svg"
    "tools", "support" -> "$DEVICON_CDN/git/git-original.svg"
    "ui" -> "$DEVICON_CDN/figma/figma-original.svg"
    "perf" -> "$DEVICON_CDN/androidstudio/androidstudio-original.svg"
    "ai" -> "$DEVICON_CDN/tensorflow/tensorflow-original.svg"
    else -> "$DEVICON_CDN/kotlin/kotlin-original.svg"
}

/** Material Symbols names for service cards/chips (matches CMP iconForKey). */
fun materialIconName(iconKey: String): String = when (iconKey) {
    "android" -> "phone_android"
    "tv" -> "home"
    "ios" -> "phone_iphone"
    "kmp", "cmp", "cross" -> "share"
    "api" -> "cloud"
    "ui" -> "edit"
    "perf", "ai" -> "star"
    "support" -> "info"
    "tools" -> "build"
    else -> "star"
}

fun serviceAccentColor(iconKey: String): String = when (iconKey) {
    "android" -> "#2D6A4F"
    "tv" -> "#4338CA"
    "kmp" -> "#7F52FF"
    "cmp" -> "#1976D2"
    "ios" -> "#3A3A3C"
    "cross" -> "#0891B2"
    "api" -> "#0369A1"
    "ui" -> "#7C3AED"
    "perf" -> "#EA580C"
    "support" -> "#059669"
    "ai" -> "#6D28D9"
    else -> "#3B82F6"
}

fun serviceGradient(iconKey: String): String = when (iconKey) {
    "android" -> "linear-gradient(135deg,#2D6A4F,#40916C)"
    "tv" -> "linear-gradient(135deg,#4338CA,#6366F1)"
    "kmp" -> "linear-gradient(135deg,#7F52FF,#E4572E)"
    "cmp" -> "linear-gradient(135deg,#1976D2,#42A5F5)"
    "ios" -> "linear-gradient(135deg,#3A3A3C,#007AFF)"
    "cross" -> "linear-gradient(135deg,#0891B2,#F59E0B)"
    "api" -> "linear-gradient(135deg,#0369A1,#38BDF8)"
    "ui" -> "linear-gradient(135deg,#7C3AED,#EC4899)"
    "perf" -> "linear-gradient(135deg,#EA580C,#FBBF24)"
    "support" -> "linear-gradient(135deg,#059669,#34D399)"
    "ai" -> "linear-gradient(135deg,#6D28D9,#A855F7)"
    else -> "linear-gradient(135deg,#3B82F6,#60A5FA)"
}
