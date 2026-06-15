package com.shashank.portfolio.util

import androidx.compose.ui.graphics.ImageBitmap

/** Loads a remote tech logo (PNG) from CDN; returns null on failure. */
expect suspend fun loadTechLogo(url: String): ImageBitmap?
