package com.shashank.portfolio.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Canvas
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.attributes.*

@Composable
fun PortfolioShell(content: @Composable () -> Unit) {
    Canvas(attrs = {
        classes("dev-bg-canvas")
        id("dev-bg")
    })
    Div(attrs = { classes("dancer-zone") }) {
        Canvas(attrs = {
            classes("dancer-canvas")
            id("dancer-canvas")
        })
    }
    Div(attrs = { classes("content-backdrop") }) {}
    Div(attrs = {
        classes("music-widget", "glass")
        id("music-widget")
    }) {
        Div(attrs = { classes("music-body") }) {
            Button(attrs = {
                classes("music-play-btn")
                id("music-play")
                attr("type", "button")
                attr("aria-label", "Play music")
            }) {
                Span(attrs = { classes("material-symbols-outlined") }) { Text("play_arrow") }
            }
            Div(attrs = { classes("music-meta") }) {
                Span(attrs = {
                    classes("music-title")
                    id("music-title")
                }) { Text("…") }
                Span(attrs = {
                    classes("music-artist")
                    id("music-artist")
                }) {}
                Span(attrs = {
                    classes("music-status")
                    id("music-status")
                }) {}
            }
            Button(attrs = {
                classes("music-playlist-btn")
                id("music-playlist-btn")
                attr("type", "button")
                attr("aria-label", "Open playlist")
                attr("aria-expanded", "false")
            }) {
                Span(attrs = { classes("material-symbols-outlined") }) { Text("queue_music") }
            }
            Div(attrs = { classes("music-volume-wrap") }) {
                Span(attrs = { classes("material-symbols-outlined", "music-vol-icon") }) { Text("volume_up") }
                Input(type = InputType.Range, attrs = {
                    classes("music-volume")
                    id("music-volume")
                    attr("min", "0")
                    attr("max", "100")
                    attr("value", "35")
                    attr("aria-label", "Music volume")
                })
            }
        }
        Div(attrs = {
            classes("music-playlist")
            id("music-playlist")
            attr("aria-label", "Song playlist")
        }) {}
    }
    Div(attrs = {
        classes("youtube-hidden")
        id("youtube-mount")
        attr("aria-hidden", "true")
    }) {}
    Div(attrs = {
        classes("youtube-hidden")
        id("youtube-probe-mount")
        attr("aria-hidden", "true")
    }) {}
    Div(attrs = { classes("page-content") }) {
        content()
    }
}
