import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.script
import kotlinx.html.unsafe

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
}

group = "com.shashank.portfolio"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Android and Multiplatform Developer portfolio — Kotlin, Jetpack Compose, KMP.")
            head.add {
                script {
                    unsafe {
                        raw(
                            """
                            try{
                              var t=localStorage.getItem('portfolio-theme')||'midnight';
                              document.documentElement.setAttribute('data-theme',t);
                            }catch(e){}
                            """.trimIndent(),
                        )
                    }
                }
                link(rel = "stylesheet", href = "/styles.css")
                link(
                    rel = "stylesheet",
                    href = "https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200&display=swap",
                )
                link(rel = "icon", href = "/favicon.svg") {
                    type = "image/svg+xml"
                }
                script(src = "/hyperspace.js") { defer = true }
                script(src = "/background.js") { defer = true }
                script(src = "/hero-solar.js") { defer = true }
                script(src = "/app.js") { defer = true }
            }
        }
    }
}

kotlin {
    configAsKobwebApplication("portfolio")

    sourceSets {
        jsMain {
            kotlin.srcDir("src/site")
        }
        jsMain.dependencies {
            implementation(project(":shared"))
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
        }
    }
}
