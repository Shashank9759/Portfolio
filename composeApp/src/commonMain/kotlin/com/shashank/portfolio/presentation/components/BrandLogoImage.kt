package com.shashank.portfolio.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shashank.portfolio.generated.resources.Res
import com.shashank.portfolio.generated.resources.brand_gmail
import com.shashank.portfolio.generated.resources.brand_github
import com.shashank.portfolio.generated.resources.brand_linkedin
import com.shashank.portfolio.generated.resources.brand_resume
import com.shashank.portfolio.generated.resources.brand_topmate
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun BrandLogoImage(
    iconKey: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    fillContainer: Boolean = false,
) {
    val bundled = brandDrawableFor(iconKey)
    val boxModifier = if (fillContainer) modifier else modifier.size(size)
    val imageModifier = if (fillContainer) Modifier.fillMaxSize() else Modifier.size(size)

    Box(
        modifier = boxModifier.clip(RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center,
    ) {
        if (bundled != null) {
            Image(
                painter = painterResource(bundled),
                contentDescription = contentDescription,
                modifier = imageModifier,
                contentScale = ContentScale.Crop,
            )
        } else {
            Icon(
                imageVector = iconForKey(iconKey),
                contentDescription = contentDescription,
                tint = Color.Unspecified,
                modifier = imageModifier,
            )
        }
    }
}

private fun brandDrawableFor(iconKey: String): DrawableResource? = when (iconKey) {
    "linkedin" -> Res.drawable.brand_linkedin
    "github" -> Res.drawable.brand_github
    "topmate" -> Res.drawable.brand_topmate
    "resume" -> Res.drawable.brand_resume
    "email" -> Res.drawable.brand_gmail
    else -> null
}
