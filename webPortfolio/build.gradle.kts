plugins {
    alias(libs.plugins.kotlinJvm)
    application
}

application {
    mainClass = "com.shashank.portfolio.web.MainKt"
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.kotlinx.html)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.cors)
}

tasks.named<JavaExec>("run") {
    dependsOn("generateWebDist")
}

/** Opens the static site in your browser — no server, no Gradle hang. */
tasks.register<Exec>("openWeb") {
    group = "web"
    description = "Generate site and open index.html in the default browser (instant)"
    dependsOn("generateWebDist")
    commandLine("open", layout.buildDirectory.file("dist/index.html").get().asFile.absolutePath)
}

tasks.register<JavaExec>("generateWebDist") {
    group = "web"
    description = "Generate static HTML/CSS site to webPortfolio/build/dist"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.shashank.portfolio.web.SiteGeneratorKt")
}
