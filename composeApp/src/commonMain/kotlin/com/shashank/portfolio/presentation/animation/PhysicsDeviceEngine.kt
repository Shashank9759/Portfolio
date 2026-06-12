package com.shashank.portfolio.presentation.animation

import androidx.compose.runtime.*
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sin
import kotlin.math.sqrt

enum class PhysicsDeviceType { PHONE, TV, TABLET }

data class PhysicsDeviceConfig(
    val id: String,
    val type: PhysicsDeviceType,
    val anchorX: Float,
    val anchorY: Float,
    val width: Float,
    val height: Float,
    val mass: Float = 1f,
    val springK: Float = 2.8f,
    val damping: Float = 0.86f,
    val mouseStrength: Float = 1.2f,
    val label: String = "",
)

data class PhysicsDeviceState(
    val config: PhysicsDeviceConfig,
    var x: Float,
    var y: Float,
    var vx: Float = 0f,
    var vy: Float = 0f,
    var tilt: Float = 0f,
    var tiltVel: Float = 0f,
) {
    fun bounds(): Rect = Rect(
        left = x - config.width / 2f,
        top = y - config.height / 2f,
        right = x + config.width / 2f,
        bottom = y + config.height / 2f,
    )
}

fun defaultPhysicsDevices(canvasW: Float, canvasH: Float): List<PhysicsDeviceConfig> {
    if (canvasW <= 0f || canvasH <= 0f) return emptyList()
    return listOf(
        PhysicsDeviceConfig("p1", PhysicsDeviceType.PHONE, canvasW * 0.88f, canvasH * 0.12f, canvasW * 0.09f, canvasH * 0.15f, mouseStrength = 1.4f, label = "Compose"),
        PhysicsDeviceConfig("p2", PhysicsDeviceType.PHONE, canvasW * 0.06f, canvasH * 0.58f, canvasW * 0.085f, canvasH * 0.14f, mouseStrength = 1.1f, label = "Kotlin"),
        PhysicsDeviceConfig("p3", PhysicsDeviceType.PHONE, canvasW * 0.76f, canvasH * 0.72f, canvasW * 0.075f, canvasH * 0.12f, mouseStrength = 1.25f, label = "KMP"),
        PhysicsDeviceConfig("tv1", PhysicsDeviceType.TV, canvasW * 0.08f, canvasH * 0.14f, canvasW * 0.18f, canvasH * 0.1f, mass = 1.4f, mouseStrength = 0.9f, label = "Leanback"),
        PhysicsDeviceConfig("tv2", PhysicsDeviceType.TV, canvasW * 0.7f, canvasH * 0.8f, canvasW * 0.16f, canvasH * 0.09f, mass = 1.3f, mouseStrength = 1f, label = "Android TV"),
        PhysicsDeviceConfig("tab1", PhysicsDeviceType.TABLET, canvasW * 0.42f, canvasH * 0.08f, canvasW * 0.12f, canvasH * 0.09f, mouseStrength = 1.15f, label = "Wasm"),
        PhysicsDeviceConfig("tab2", PhysicsDeviceType.TABLET, canvasW * 0.22f, canvasH * 0.82f, canvasW * 0.11f, canvasH * 0.085f, mouseStrength = 1.05f, label = "CMP"),
    )
}

@Composable
fun rememberPhysicsDevices(
    canvasWidth: Float,
    canvasHeight: Float,
    pointer: Offset,
    scrollOffset: Int = 0,
): List<PhysicsDeviceState> {
    val configs = remember(canvasWidth, canvasHeight) {
        defaultPhysicsDevices(canvasWidth, canvasHeight)
    }

    var states by remember(canvasWidth, canvasHeight) {
        mutableStateOf(
            configs.map { cfg ->
                PhysicsDeviceState(config = cfg, x = cfg.anchorX, y = cfg.anchorY)
            },
        )
    }

    LaunchedEffect(canvasWidth, canvasHeight) {
        states = configs.map { cfg ->
            PhysicsDeviceState(config = cfg, x = cfg.anchorX, y = cfg.anchorY)
        }
    }

    LaunchedEffect(canvasWidth, canvasHeight, pointer, scrollOffset) {
        var lastFrame = 0L
        while (true) {
            withFrameNanos { frameTime ->
                val dt = if (lastFrame == 0L) {
                    0.016f
                } else {
                    ((frameTime - lastFrame) / 1_000_000_000f).coerceIn(0.008f, 0.032f)
                }
                lastFrame = frameTime

                val scrollShift = scrollOffset * 0.04f
                val next = states.map { state ->
                    val cfg = state.config
                    val anchorY = cfg.anchorY - scrollShift * (if (cfg.type == PhysicsDeviceType.TV) 0.5f else 1f)
                    val anchor = Offset(cfg.anchorX, anchorY)

                    var ax = cfg.springK * (anchor.x - state.x)
                    var ay = cfg.springK * (anchor.y - state.y)

                    if (pointer != Offset.Zero) {
                        val dx = state.x - pointer.x
                        val dy = state.y - pointer.y
                        val dist = sqrt(dx * dx + dy * dy).coerceAtLeast(1f)
                        val influence = 220f * cfg.mouseStrength
                        when {
                            dist < 90f -> {
                                val push = (90f - dist) / 90f * 180f * cfg.mouseStrength
                                ax += dx / dist * push
                                ay += dy / dist * push
                            }
                            dist < influence -> {
                                val pull = (1f - dist / influence) * 55f * cfg.mouseStrength
                                ax -= dx / dist * pull
                                ay -= dy / dist * pull
                            }
                        }
                    }

                    var vx = (state.vx + ax * dt / cfg.mass) * cfg.damping
                    var vy = (state.vy + ay * dt / cfg.mass) * cfg.damping
                    val x = state.x + vx * dt * 60f
                    val y = state.y + vy * dt * 60f

                    val targetTilt = if (pointer != Offset.Zero) {
                        atan2(pointer.y - y, pointer.x - x) * (8f / PI.toFloat()) * cfg.mouseStrength
                    } else {
                        sin(frameTime / 1_000_000_000.0 + cfg.anchorX.toDouble()).toFloat() * 3f
                    }
                    var tiltVel = (state.tiltVel + (targetTilt - state.tilt) * 4f * dt) * 0.82f
                    val tilt = state.tilt + tiltVel * dt * 60f

                    PhysicsDeviceState(cfg, x, y, vx, vy, tilt, tiltVel)
                }
                states = next
            }
        }
    }

    return states
}
