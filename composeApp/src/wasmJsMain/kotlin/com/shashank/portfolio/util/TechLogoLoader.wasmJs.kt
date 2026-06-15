package com.shashank.portfolio.util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.jetbrains.skia.Image
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.khronos.webgl.get
import org.w3c.fetch.Response

actual suspend fun loadTechLogo(url: String): ImageBitmap? {
    return try {
        val response: Response = window.fetch(url).await()
        if (!response.ok) return null
        val buffer: ArrayBuffer = response.arrayBuffer().await()
        val int8 = Int8Array(buffer)
        val bytes = ByteArray(int8.length) { i -> int8[i].toByte() }
        Image.makeFromEncoded(bytes).toComposeImageBitmap()
    } catch (_: Throwable) {
        null
    }
}
