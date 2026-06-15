package com.shashank.portfolio.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.shashank.portfolio.generated.resources.Res
import com.shashank.portfolio.generated.resources.org_putatoe
import com.shashank.portfolio.generated.resources.project_codersaidhub
import com.shashank.portfolio.generated.resources.project_cricradio
import com.shashank.portfolio.generated.resources.project_rrbmustudies
import com.shashank.portfolio.generated.resources.project_skinlens
import com.shashank.portfolio.generated.resources.project_yaaddiary
import com.shashank.portfolio.util.ProjectLogoUrls
import com.shashank.portfolio.util.loadTechLogo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProjectImage(
    imageKey: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val drawable = projectDrawableFor(imageKey)
    if (drawable != null) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(drawable),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = contentScale,
            )
        }
        return
    }

    val remoteUrl = remember(imageKey) { ProjectLogoUrls.urlFor(imageKey) }
    if (remoteUrl != null) {
        ProjectRemoteImage(imageKey = imageKey, url = remoteUrl, modifier = modifier)
        return
    }

    ProjectPlaceholderImage(imageKey = imageKey, modifier = modifier)
}

@Composable
private fun ProjectRemoteImage(
    imageKey: String,
    url: String,
    modifier: Modifier = Modifier,
) {
    var bitmap by remember(url) { mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null) }

    LaunchedEffect(url) {
        bitmap = withContext(Dispatchers.Default) { loadTechLogo(url) }
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap!!,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
            )
        } else {
            ProjectPlaceholderImage(imageKey = imageKey, modifier = Modifier.fillMaxSize())
        }
    }
}

private fun projectDrawableFor(imageKey: String): DrawableResource? = when (imageKey) {
    "cricradio" -> Res.drawable.project_cricradio
    "codersaidhub" -> Res.drawable.project_codersaidhub
    "rrbmustudies" -> Res.drawable.project_rrbmustudies
    "skinlens" -> Res.drawable.project_skinlens
    "putatoe" -> Res.drawable.org_putatoe
    "yaaddiary" -> Res.drawable.project_yaaddiary
    else -> null
}
