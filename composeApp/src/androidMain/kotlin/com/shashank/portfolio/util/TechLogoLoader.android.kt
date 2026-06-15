package com.shashank.portfolio.util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import java.net.URL

actual suspend fun loadTechLogo(url: String): ImageBitmap? = try {
    val bytes = URL(url).readBytes()
    Image.makeFromEncoded(bytes).toComposeImageBitmap()
} catch (_: Exception) {
    null
}
