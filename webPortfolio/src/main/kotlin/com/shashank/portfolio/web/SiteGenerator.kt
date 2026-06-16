package com.shashank.portfolio.web

import com.shashank.portfolio.data.source.PortfolioDataSource
import com.shashank.portfolio.domain.model.*
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import java.io.File

private val heroTechStack = listOf(
    "Kotlin", "Jetpack Compose", "Android TV", "Leanback", "KMP", "CMP", "SwiftUI", "UIKit",
)

private val navSections = listOf(
    "about" to "About",
    "skills" to "Skills",
    "experience" to "Experience",
    "organizations" to "Clients",
    "projects" to "Projects",
    "services" to "Services",
    "testimonials" to "Testimonials",
    "contact" to "Contact",
)

fun main() {
    val dist = SiteGenerator.generate()
    println("Generated static site at ${dist.absolutePath}")
}

object SiteGenerator {
    private val webResources = listOf("themes.css", "animations.css", "styles.css", "app.js", "background.js", "music-player.js", "favicon.svg")

    private fun distDirectory(): File {
        var dir = File(System.getProperty("user.dir"))
        while (dir != null && !dir.resolve("settings.gradle.kts").isFile) {
            dir = dir.parentFile
        }
        val root = dir ?: File(".")
        return root.resolve("webPortfolio/build/dist").absoluteFile
    }

    fun generate(): File {
        val data = PortfolioDataSource.getPortfolioData()
        val distDir = distDirectory()
        val assetsDir = distDir.resolve("assets")
        distDir.mkdirs()

        copyAssetsFromClasspath(assetsDir)
        webResources.forEach { name ->
            distDir.resolve(name).writeText(readResource("web/$name"))
        }

        val html = createHTML(prettyPrint = true).html {
            lang = "en"
            attributes["data-theme"] = "midnight"
            head {
                meta(charset = "UTF-8")
                meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
                meta(
                    name = "description",
                    content = "${data.personalInfo.name} - ${data.personalInfo.title}. Portfolio showcasing Kotlin, Jetpack Compose, KMP, and mobile development.",
                )
                title { +"${data.personalInfo.name} | Mobile Dev" }
                link(rel = "icon", href = "favicon.svg", type = "image/svg+xml")
                link(
                    rel = "stylesheet",
                    href = "https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200&display=swap",
                )
                link(rel = "stylesheet", href = "themes.css")
                link(rel = "stylesheet", href = "animations.css")
                link(rel = "stylesheet", href = "styles.css")
            }
            body {
                canvas(classes = "dev-bg-canvas") { attributes["id"] = "dev-bg" }
                div(classes = "dancer-zone") {
                    canvas(classes = "dancer-canvas") { attributes["id"] = "dancer-canvas" }
                }
                div(classes = "content-backdrop") {}
                div(classes = "music-widget glass") {
                    attributes["id"] = "music-widget"
                    button(type = ButtonType.button, classes = "music-play-btn") {
                        attributes["id"] = "music-play"
                        attributes["aria-label"] = "Play Sahiba by Aditya Rikhari"
                        span(classes = "material-symbols-outlined") { +"play_arrow" }
                    }
                    div(classes = "music-meta") {
                        span(classes = "music-title") { +"Sahiba" }
                        span(classes = "music-artist") { +"Aditya Rikhari" }
                        span(classes = "music-status") { attributes["id"] = "music-status" }
                    }
                    div(classes = "music-volume-wrap") {
                        span(classes = "material-symbols-outlined music-vol-icon") { +"volume_up" }
                        input(type = InputType.range, classes = "music-volume") {
                            attributes["id"] = "music-volume"
                            attributes["min"] = "0"
                            attributes["max"] = "100"
                            attributes["value"] = "35"
                            attributes["aria-label"] = "Music volume"
                        }
                    }
                }
                div(classes = "youtube-hidden") {
                    attributes["id"] = "youtube-mount"
                    attributes["aria-hidden"] = "true"
                }
                div(classes = "page-content") {
                    header(classes = "site-header") {
                        div(classes = "container nav-inner") {
                            a(href = "#home", classes = "nav-logo") { +"SR" }
                            nav(classes = "desktop-nav") {
                                navSections.forEach { (id, label) ->
                                    a(href = "#$id", classes = "nav-link") { +label }
                                }
                            }
                            div(classes = "nav-actions") {
                                div(classes = "theme-picker") {
                                    button(classes = "theme-chip", type = ButtonType.button) {
                                        attributes["id"] = "theme-chip"
                                        span(classes = "theme-dot") {}
                                        span { attributes["id"] = "theme-label"; +"Midnight" }
                                    }
                                    div(classes = "theme-menu") { attributes["id"] = "theme-menu" }
                                }
                                a(href = "#contact", classes = "btn btn-gradient btn-sm") { +"Hire Me" }
                                button(classes = "menu-toggle", type = ButtonType.button) {
                                    attributes["aria-label"] = "Open menu"
                                    span(classes = "menu-bar") {}
                                    span(classes = "menu-bar") {}
                                    span(classes = "menu-bar") {}
                                }
                            }
                        }
                        nav(classes = "mobile-nav") {
                            a(href = "#home", classes = "nav-link") { +"Home" }
                            navSections.forEach { (id, label) ->
                                a(href = "#$id", classes = "nav-link") { +label }
                            }
                        }
                    }

                    main {
                        section(classes = "section hero-section reveal") {
                            attributes["id"] = "home"
                            renderHero(data)
                        }
                        section(classes = "section reveal") {
                            attributes["id"] = "about"
                            renderAbout(data)
                        }
                        section(classes = "section reveal") {
                            attributes["id"] = "skills"
                            renderSkills(data)
                        }
                        section(classes = "section reveal") {
                            attributes["id"] = "experience"
                            renderExperience(data)
                        }
                        section(classes = "section reveal") {
                            attributes["id"] = "organizations"
                            renderOrganizations(data)
                        }
                        section(classes = "section reveal") {
                            attributes["id"] = "projects"
                            renderProjects(data)
                        }
                        section(classes = "section reveal") {
                            attributes["id"] = "services"
                            renderServices(data)
                        }
                        section(classes = "section reveal") {
                            attributes["id"] = "testimonials"
                            renderTestimonials(data)
                        }
                        section(classes = "section reveal") {
                            attributes["id"] = "contact"
                            renderContact(data)
                        }
                    }

                    footer(classes = "site-footer") {
                        renderFooter(data)
                    }

                    a(href = "#contact", classes = "fab") {
                        attributes["title"] = "Contact"
                        span(classes = "label") { +"Let's Talk" }
                    }
                }
                script(src = "background.js") {}
                script(src = "music-player.js") {}
                script(src = "app.js") {}
            }
        }

        distDir.resolve("index.html").writeText("<!DOCTYPE html>\n$html")
        return distDir
    }

    private fun copyAssetsFromClasspath(distAssets: File) {
        val url = SiteGenerator::class.java.classLoader.getResource("assets") ?: return
        copyBundledAssets(distAssets, File(url.toURI()))
    }

    private fun readResource(path: String): String {
        val stream = SiteGenerator::class.java.classLoader.getResourceAsStream(path)
            ?: error("Missing resource: $path")
        return stream.bufferedReader().readText()
    }
}

private fun FlowContent.sectionHeader(title: String, centered: Boolean = false) {
    val wrapClass = if (centered) "section-title-wrap center" else "section-title-wrap"
    div(classes = wrapClass) {
        h2(classes = "section-title" + if (centered) " center" else "") { +title }
        span(classes = "section-underline") {}
    }
}

private fun FlowContent.renderHero(data: PortfolioData) {
    val info = data.personalInfo
    div(classes = "container hero-wrap") {
        div(classes = "recruiter-bar glass reveal") {
            span(classes = "recruiter-item") { +"Open to freelance & full-time" }
            span { +"•" }
            a(href = "mailto:${info.email}", classes = "recruiter-item clickable") { +info.email }
            span { +"•" }
            span(classes = "recruiter-item") { +info.location }
            a(href = info.linkedIn, classes = "recruiter-item clickable", target = "_blank") {
                attributes["rel"] = "noopener noreferrer"
                +"LinkedIn"
            }
            a(href = info.github, classes = "recruiter-item clickable", target = "_blank") {
                attributes["rel"] = "noopener noreferrer"
                +"GitHub"
            }
        }

        div(classes = "hero-grid") {
            div(classes = "hero-copy") {
                span(classes = "status-badge reveal") {
                    span(classes = "status-dot") {}
                    +"Available for freelance & full-time"
                }
                h1(classes = "hero-name reveal") { +info.name }
                h2(classes = "hero-title reveal") { +info.title }
                p(classes = "hero-summary reveal") { +info.summary }

                div(classes = "tech-pills") {
                    heroTechStack.forEachIndexed { i, tech ->
                        span(classes = "tech-pill reveal") {
                            style = "--reveal-delay:${i * 60}ms"
                            +tech
                        }
                    }
                }

                div(classes = "hero-actions reveal") {
                    a(href = "#contact", classes = "btn btn-gradient") { +"Hire Me" }
                    a(href = info.resumeUrl, classes = "btn btn-outline", target = "_blank") {
                        attributes["rel"] = "noopener noreferrer"
                        +"View Resume"
                    }
                    a(href = "#contact", classes = "btn btn-outline") { +"Contact" }
                }
            }

            div(classes = "hero-avatar reveal") {
                div(classes = "avatar-wrap") {
                    div(classes = "avatar-glow") {}
                    div(classes = "avatar-ring") {
                        div(classes = "avatar-core") {
                            div(classes = "avatar-logos") {
                                heroLogoFiles().forEach { file ->
                                    img(src = heroLogoSrc(file), alt = "", classes = "avatar-logo")
                                }
                            }
                            p(classes = "avatar-caption") { +"Mobile · Multiplatform · AI" }
                        }
                    }
                }
            }
        }

        div(classes = "stats-divider") {}
        div(classes = "stats-grid") {
            data.stats.forEachIndexed { i, stat ->
                div(classes = "stat-card glass reveal") {
                    style = "--reveal-delay:${i * 100}ms"
                    div(classes = "stat-value") {
                        attributes["data-target"] = stat.value.toString()
                        attributes["data-suffix"] = stat.suffix
                        if (stat.showStarIcon) attributes["data-star"] = "true"
                        +"0${stat.suffix}"
                    }
                    div(classes = "stat-label") { +stat.label }
                }
            }
        }
    }
}

private fun FlowContent.renderAbout(data: PortfolioData) {
    div(classes = "container") {
        sectionHeader("About Me")
        div(classes = "about-grid") {
            div(classes = "glass card reveal") {
                p(classes = "about-text") { +data.aboutSummary }
                h3(classes = "subsection-title") { +"Career Highlights" }
                ul(classes = "bullet-list") {
                    data.careerHighlights.forEach { item -> li { +item } }
                }
            }
            div(classes = "glass card reveal") {
                h3(classes = "subsection-title") { +"Education" }
                data.education.forEach { edu ->
                    h4 { +edu.institution }
                    p(classes = "muted") { +edu.degree }
                    p(classes = "muted small") { +"${edu.location} · ${edu.graduationDate}" }
                }
            }
        }
    }
}

private fun FlowContent.renderSkills(data: PortfolioData) {
    div(classes = "container") {
        sectionHeader("Skills & Expertise", centered = true)
        div(classes = "grid skills-grid") {
            data.skillCategories.forEachIndexed { i, category ->
                div(classes = "glass card skill-card reveal") {
                    style = "--reveal-delay:${i * 80}ms"
                    div(classes = "skill-header skill-header-shimmer") {
                        style = "background:${skillGradient(category.icon)}"
                        div(classes = "skill-header-icon") {
                            span(classes = "material-symbols-outlined skill-header-mat-icon") {
                                +materialIconName(category.icon)
                            }
                        }
                        div(classes = "skill-header-text") {
                            h3 { +category.name }
                            p { +skillTagline(category.icon) }
                        }
                    }
                    category.skills.forEach { skill ->
                        div(classes = "skill-row") {
                            div(classes = "skill-head") {
                                span { +skill.name }
                                span(classes = "skill-pct") { +"${skill.proficiency}%" }
                            }
                            div(classes = "skill-track") {
                                div(classes = "skill-fill") {
                                    style = "--skill-width:${skill.proficiency}%"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun FlowContent.renderExperience(data: PortfolioData) {
    div(classes = "container") {
        sectionHeader("Work Experience", centered = true)
        div(classes = "stack") {
            data.experience.forEachIndexed { i, exp ->
                article(classes = "glass card experience-card reveal") {
                    style = "--reveal-delay:${i * 150}ms"
                    div(classes = "exp-header") {
                        div {
                            h3 { +exp.role }
                            p(classes = "company") { +exp.company }
                            p(classes = "muted small") { +"${exp.location} · ${exp.period}" }
                        }
                        exp.link?.let { link ->
                            a(href = link, classes = "icon-link", target = "_blank") {
                                attributes["rel"] = "noopener noreferrer"
                                attributes["aria-label"] = "Open link"
                                +"↗"
                            }
                        }
                    }
                    ul(classes = "bullet-list compact") {
                        exp.highlights.forEach { h -> li { +h } }
                    }
                }
            }
        }
    }
}

private fun FlowContent.renderOrganizations(data: PortfolioData) {
    div(classes = "container") {
        sectionHeader("Helping Organisations Build Apps", centered = true)
        p(classes = "section-subtitle center") {
            +"Partnering with teams and institutions to ship production-ready mobile products."
        }
        div(classes = "grid org-grid") {
            data.clientOrganizations.forEachIndexed { i, org ->
                article(classes = "glass card org-card reveal") {
                    style = "--reveal-delay:${i * 120}ms"
                    img(src = imageSrc(org.imageKey), alt = org.appName, classes = "card-image org-image")
                    p(classes = "company") { +org.name }
                    h3 { +org.appName }
                    p(classes = "muted") { +org.description }
                    org.link?.let { link ->
                        a(href = link, classes = "btn btn-outline btn-sm", target = "_blank") {
                            attributes["rel"] = "noopener noreferrer"
                            +"View app"
                        }
                    }
                }
            }
        }
    }
}

private fun FlowContent.renderProjects(data: PortfolioData) {
    div(classes = "container") {
        sectionHeader("Featured Projects", centered = true)
        div(classes = "grid project-grid") {
            data.projects.forEachIndexed { i, project ->
                article(classes = "glass card project-card reveal") {
                    style = "--reveal-delay:${i * 120}ms"
                    img(src = imageSrc(project.imageKey), alt = project.name, classes = "card-image")
                    h3 { +project.name }
                    p(classes = "muted") { +project.description }
                    if (project.highlights.isNotEmpty()) {
                        ul(classes = "bullet-list compact") {
                            project.highlights.take(2).forEach { h -> li { +h } }
                        }
                    }
                    div(classes = "chips") {
                        project.technologies.take(4).forEach { tech ->
                            span(classes = "chip") { +tech }
                        }
                    }
                    div(classes = "card-actions") {
                        project.githubUrl?.let { url ->
                            a(href = url, classes = "btn btn-outline btn-sm", target = "_blank") {
                                attributes["rel"] = "noopener noreferrer"
                                +"GitHub"
                            }
                        }
                        project.liveUrl?.let { url ->
                            a(href = url, classes = "btn btn-outline btn-sm", target = "_blank") {
                                attributes["rel"] = "noopener noreferrer"
                                +"Live"
                            }
                        }
                        project.demoUrl?.let { url ->
                            a(href = url, classes = "btn btn-outline btn-sm", target = "_blank") {
                                attributes["rel"] = "noopener noreferrer"
                                +"Demo"
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun FlowContent.renderServices(data: PortfolioData) {
    div(classes = "container") {
        sectionHeader("Freelancing Services", centered = true)
        p(classes = "section-subtitle center") {
            +"Select a service to explore — the featured panel updates automatically."
        }

        div(classes = "service-stats glass reveal") {
            div(classes = "service-stat") {
                strong { +"${data.freelanceServices.size}" }
                span { +"Services" }
            }
            div(classes = "service-stat") { strong { +"50+" }; span { +"Projects" } }
            div(classes = "service-stat") { strong { +"100K+" }; span { +"Downloads" } }
            div(classes = "service-stat") { strong { +"24h" }; span { +"Response" } }
        }

        div(classes = "service-chips reveal") {
            data.freelanceServices.forEachIndexed { index, service ->
                button(
                    type = ButtonType.button,
                    classes = "chip service-chip" + if (index == 0) " active" else "",
                ) {
                    attributes["data-service-index"] = index.toString()
                    span(classes = "service-chip-icon-wrap") {
                        style = "background:${serviceGradient(service.icon)}"
                        span(classes = "material-symbols-outlined service-chip-icon") {
                            +materialIconName(service.icon)
                        }
                    }
                    +service.title
                }
            }
        }

        div(classes = "service-panels-wrap reveal") {
            data.freelanceServices.forEachIndexed { index, service ->
                article(classes = "glass card service-panel" + if (index == 0) " active" else "") {
                    attributes["data-service-panel"] = index.toString()
                    div(classes = "featured-panel") {
                        div(classes = "featured-icon") {
                            style = "background:${serviceGradient(service.icon)}"
                            span(classes = "material-symbols-outlined featured-mat-icon") {
                                +materialIconName(service.icon)
                            }
                        }
                        div {
                            p(classes = "featured-label") { +"Featured Service" }
                            h3 { +service.title }
                            p(classes = "muted") { +service.description }
                            if (service.deliveryHint.isNotBlank()) {
                                p(classes = "delivery-hint") { +service.deliveryHint }
                            }
                            div(classes = "chips") {
                                service.tags.forEach { tag -> span(classes = "chip") { +tag } }
                            }
                        }
                    }
                }
            }
        }

        div(classes = "service-grid") {
            data.freelanceServices.forEachIndexed { index, service ->
                val preview = buildString {
                    append(service.description.take(90))
                    if (service.description.length > 90) append('…')
                }
                article(classes = "glass card service-grid-card reveal" + if (index == 0) " selected" else "") {
                    style = "--reveal-delay:${index * 80}ms;--service-accent:${serviceAccentColor(service.icon)}"
                    div(classes = "service-grid-head") {
                        div(classes = "service-grid-icon-wrap") {
                            style = "background:${serviceGradient(service.icon)}"
                            span(classes = "material-symbols-outlined service-grid-mat-icon") {
                                +materialIconName(service.icon)
                            }
                        }
                        div(classes = "service-grid-title-wrap") {
                            strong { +service.title }
                            if (service.deliveryHint.isNotBlank()) {
                                p(classes = "delivery-hint small") { +service.deliveryHint }
                            }
                        }
                        span(classes = "material-symbols-outlined service-grid-check") { +"check_circle" }
                    }
                    p(classes = "muted small") { +preview }
                }
            }
        }
    }
}

private fun FlowContent.renderTestimonials(data: PortfolioData) {
    div(classes = "container") {
        sectionHeader("Client Testimonials", centered = true)
        p(classes = "section-subtitle center") {
            +"What clients and collaborators say about working together."
        }
        div(classes = "grid testimonial-grid") {
            data.testimonials.forEachIndexed { i, t ->
                div(classes = "glass card testimonial-card reveal") {
                    style = "--reveal-delay:${i * 100}ms"
                    div(classes = "stars") {
                        repeat(t.rating) { span(classes = "star") { +"★" } }
                        if (t.isPlaceholder) span(classes = "placeholder-badge") { +"Sample" }
                    }
                    p(classes = "quote") { +"\"${t.content}\"" }
                    p(classes = "testimonial-author") {
                        strong { +t.clientName }
                        span(classes = "muted") { +" — ${t.role}, ${t.company}" }
                    }
                }
            }
        }
    }
}

private fun FlowContent.renderContact(data: PortfolioData) {
    val info = data.personalInfo
    div(classes = "container") {
        sectionHeader("Get In Touch", centered = true)
        p(classes = "section-subtitle center") {
            +"Recruiters & clients — reach out directly. I typically respond within 24 hours."
        }

        div(classes = "contact-grid") {
            div(classes = "glass card contact-info reveal") {
                h3 { +"Let's work together" }
                p(classes = "muted") { +"Recruiters & clients — reach out directly. I typically respond within 24 hours." }
                p { a(href = "mailto:${info.email}") { +info.email } }
                p { a(href = "tel:${info.phone}") { +info.phone } }
                p(classes = "muted") { +info.location }

                h4 { +"Social Links" }
                div(classes = "social-grid") {
                    data.socialLinks.forEach { link ->
                        val icon = socialIconSrc(link.icon)
                        a(href = link.url, classes = "social-card glass", target = "_blank") {
                            attributes["rel"] = "noopener noreferrer"
                            if (icon != null) {
                                img(src = icon, alt = link.name, classes = "social-icon")
                            }
                            span { +link.name }
                        }
                    }
                }
            }

            form(classes = "glass card contact-form reveal") {
                attributes["id"] = "contact-form"
                attributes["data-email"] = info.email
                h3 { +"Send a Message" }
                label {
                    +"Name"
                    input(type = InputType.text, name = "name") {
                        attributes["required"] = "true"
                        placeholder = "Your name"
                    }
                }
                label {
                    +"Subject"
                    input(type = InputType.text, name = "subject") {
                        attributes["required"] = "true"
                        placeholder = "Project or role"
                    }
                }
                label {
                    +"Message"
                    textArea(rows = "5", cols = "10") {
                        attributes["name"] = "message"
                        attributes["required"] = "true"
                        attributes["placeholder"] = "Tell me about your project..."
                    }
                }
                p(classes = "form-error hidden") {
                    attributes["id"] = "form-error"
                }
                button(type = ButtonType.submit, classes = "btn btn-gradient") { +"Send via Email" }
            }
        }
    }
}

private fun FlowContent.renderFooter(data: PortfolioData) {
    val info = data.personalInfo
    val footerLinks = listOf(
        Triple("Resume", "resume", info.resumeUrl),
        Triple("LinkedIn", "linkedin", info.linkedIn),
        Triple("GitHub", "github", info.github),
        Triple("Topmate", "topmate", "https://topmate.io/shashank_ranjan10"),
        Triple("Email", "email", "mailto:${info.email}"),
    )

    div(classes = "container footer-inner") {
        h3(classes = "footer-name") { +info.name }
        p(classes = "footer-title") { +info.title }

        h4(classes = "footer-connect") { +"Connect with me" }

        div(classes = "footer-social-row") {
            footerLinks.forEach { (label, iconKey, url) ->
                val iconSrc = socialIconSrc(iconKey)
                a(href = url, classes = "footer-social-card") {
                    if (!url.startsWith("mailto:")) {
                        attributes["target"] = "_blank"
                        attributes["rel"] = "noopener noreferrer"
                    }
                    span(classes = "footer-social-icon-wrap") {
                        if (iconSrc != null) {
                            img(src = iconSrc, alt = label, classes = "footer-social-icon")
                        }
                    }
                    span(classes = "footer-social-label") { +label }
                }
            }
        }

        hr(classes = "footer-divider") {}

        p(classes = "footer-copyright") {
            +"© ${java.time.Year.now().value} ${info.name}. All rights reserved."
        }
        p(classes = "footer-built") { +"Built with semantic HTML & CSS · Kotlin kotlinx-html" }
    }
}
