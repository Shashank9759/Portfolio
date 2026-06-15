package com.shashank.portfolio.util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import java.net.URI

actual suspend fun loadTechLogo(url: String): ImageBitmap? = try {
    val bytes = URI(url).toURL().readBytes()
    Image.makeFromEncoded(bytes).toComposeImageBitmap()
} catch (_: Exception) {
    null
}
