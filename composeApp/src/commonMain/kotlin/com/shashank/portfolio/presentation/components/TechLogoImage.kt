package com.shashank.portfolio.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.util.SkillImageUrls
import com.shashank.portfolio.util.loadTechLogo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun TechLogoImage(
    iconKey: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    fallbackTint: Color = Color.White,
    loadRemote: Boolean = true,
) {
    if (!loadRemote) {
        Icon(
            imageVector = iconForKey(iconKey),
            contentDescription = contentDescription,
            tint = fallbackTint,
            modifier = modifier,
        )
        return
    }
    val url = remember(iconKey) { SkillImageUrls.urlFor(iconKey) }
    var bitmap by remember(url) { mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null) }
    var loading by remember(url) { mutableStateOf(true) }

    LaunchedEffect(url) {
        loading = true
        bitmap = withContext(Dispatchers.Default) { loadTechLogo(url) }
        loading = false
    }

    when {
        bitmap != null -> Image(
            bitmap = bitmap!!,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Fit,
        )
        loading -> Icon(
            imageVector = iconForKey(iconKey),
            contentDescription = contentDescription,
            tint = fallbackTint.copy(alpha = 0.7f),
            modifier = modifier,
        )
        else -> Icon(
            imageVector = iconForKey(iconKey),
            contentDescription = contentDescription,
            tint = fallbackTint,
            modifier = modifier,
        )
    }
}
