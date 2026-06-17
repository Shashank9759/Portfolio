package com.shashank.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.varabyte.kobweb.core.App

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
  // CSS/scripts are in index <head> via build.gradle.kts.
  // Re-run portfolio boot after Kobweb Compose mounts page content.
  SideEffect {
    js("if (typeof window.portfolioBackgroundInit === 'function') window.portfolioBackgroundInit()")
    js("if (typeof window.portfolioHeroSolarInit === 'function') window.portfolioHeroSolarInit()")
    js("if (typeof window.portfolioBoot === 'function') window.portfolioBoot()")
  }
  content()
}
