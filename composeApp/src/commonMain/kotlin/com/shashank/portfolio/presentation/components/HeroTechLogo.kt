package com.shashank.portfolio.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.shashank.portfolio.generated.resources.Res
import com.shashank.portfolio.generated.resources.hero_ai
import com.shashank.portfolio.generated.resources.hero_android
import com.shashank.portfolio.generated.resources.hero_kotlin
import org.jetbrains.compose.resources.painterResource

@Composable
fun HeroTechLogo(
    iconKey: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    val drawable = when (iconKey) {
        "android" -> Res.drawable.hero_android
        "kmp", "cmp" -> Res.drawable.hero_kotlin
        "ai" -> Res.drawable.hero_ai
        else -> Res.drawable.hero_android
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(drawable),
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit,
        )
    }
}
