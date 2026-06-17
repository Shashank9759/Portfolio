package com.shashank.portfolio.components.sections

import androidx.compose.runtime.Composable
import com.shashank.portfolio.*
import com.shashank.portfolio.domain.model.*
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.dom.*
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

@Composable
fun SectionHeader(title: String, centered: Boolean = false) {
    Div(attrs = {
        classes("section-title-wrap", *(if (centered) arrayOf("center") else emptyArray()))
    }) {
        H2(attrs = {
            classes("section-title", *(if (centered) arrayOf("center") else emptyArray()))
        }) { Text(title) }
        Span(attrs = { classes("section-underline") }) {}
    }
}

@Composable
fun HeroSection(data: PortfolioData) {
    val info = data.personalInfo
    Section(attrs = {
        classes("section", "hero-section", "reveal")
        id("home")
    }) {
        Div(attrs = { classes("container", "hero-wrap") }) {
            Div(attrs = { classes("recruiter-bar", "glass", "reveal") }) {
                Span(attrs = { classes("recruiter-item") }) { Text("Open to freelance & full-time") }
                Text("•")
                A(href = gmailWebComposeUrl(info.email), attrs = {
                    classes("recruiter-item", "clickable")
                    attr("target", "_blank")
                    attr("rel", "noopener noreferrer")
                }) { Text(info.email) }
                Text("•")
                Span(attrs = { classes("recruiter-item") }) { Text(info.location) }
                A(href = info.linkedIn, attrs = {
                    classes("recruiter-item", "clickable")
                    attr("target", "_blank")
                    attr("rel", "noopener noreferrer")
                }) { Text("LinkedIn") }
                A(href = info.github, attrs = {
                    classes("recruiter-item", "clickable")
                    attr("target", "_blank")
                    attr("rel", "noopener noreferrer")
                }) { Text("GitHub") }
            }

            Div(attrs = { classes("hero-grid") }) {
                Div(attrs = { classes("hero-copy") }) {
                    Span(attrs = { classes("status-badge", "reveal") }) {
                        Span(attrs = { classes("status-dot") }) {}
                        Text("Available for freelance & full-time")
                    }
                    H1(attrs = { classes("hero-name", "reveal") }) { Text(info.name) }
                    H2(attrs = { classes("hero-title", "reveal") }) { Text(info.title) }
                    P(attrs = { classes("hero-summary", "reveal") }) { Text(info.summary) }

                    Div(attrs = { classes("tech-pills") }) {
                        heroTechStack.forEachIndexed { i, tech ->
                            Span(attrs = {
                                classes("tech-pill", "reveal")
                                style { property("--reveal-delay", "${i * 60}ms") }
                            }) { Text(tech) }
                        }
                    }

                    Div(attrs = { classes("hero-actions", "reveal") }) {
                        A(href = "#contact", attrs = { classes("btn", "btn-gradient") }) { Text("Hire Me") }
                        A(href = info.resumeUrl, attrs = {
                            classes("btn", "btn-outline")
                            attr("target", "_blank")
                            attr("rel", "noopener noreferrer")
                        }) { Text("View Resume") }
                        A(href = "#contact", attrs = { classes("btn", "btn-outline") }) { Text("Contact") }
                    }
                }

                Div(attrs = { classes("hero-avatar", "reveal") }) {
                    Div(attrs = { classes("avatar-wrap", "solar-portal") }) {
                        Div(attrs = { classes("avatar-glow") }) {}
                        Div(attrs = { classes("avatar-ring") }) {
                            Div(attrs = { classes("avatar-core") }) {
                                Canvas(attrs = {
                                    classes("hero-solar-canvas")
                                    id("hero-solar")
                                })
                                P(attrs = { classes("avatar-caption") }) { Text("Mobile · Multiplatform · AI") }
                            }
                        }
                    }
                }
            }

            Div(attrs = { classes("stats-divider") }) {}
            Div(attrs = { classes("stats-grid") }) {
                data.stats.forEachIndexed { i, stat ->
                    Div(attrs = {
                        classes("stat-card", "glass", "reveal")
                        style { property("--reveal-delay", "${i * 100}ms") }
                    }) {
                        Div(attrs = {
                            classes("stat-value")
                            attr("data-target", stat.value.toString())
                            attr("data-suffix", stat.suffix)
                            if (stat.showStarIcon) attr("data-star", "true")
                        }) { Text("0${stat.suffix}") }
                        Div(attrs = { classes("stat-label") }) { Text(stat.label) }
                    }
                }
            }
        }
    }
}

@Composable
fun AboutSection(data: PortfolioData) {
    Section(attrs = {
        classes("section", "reveal")
        id("about")
    }) {
        Div(attrs = { classes("container") }) {
            SectionHeader("About Me")
            Div(attrs = { classes("about-grid") }) {
                Div(attrs = { classes("glass", "card", "reveal") }) {
                    P(attrs = { classes("about-text") }) { Text(data.aboutSummary) }
                    H3(attrs = { classes("subsection-title") }) { Text("Career Highlights") }
                    Ul(attrs = { classes("bullet-list") }) {
                        data.careerHighlights.forEach { item ->
                            Li { Text(item) }
                        }
                    }
                }
                Div(attrs = { classes("glass", "card", "reveal") }) {
                    H3(attrs = { classes("subsection-title") }) { Text("Education") }
                    data.education.forEach { edu ->
                        H4 { Text(edu.institution) }
                        P(attrs = { classes("muted") }) { Text(edu.degree) }
                        P(attrs = { classes("muted", "small") }) { Text("${edu.location} · ${edu.graduationDate}") }
                    }
                }
            }
        }
    }
}

@Composable
fun SkillsSection(data: PortfolioData) {
    Section(attrs = {
        classes("section", "reveal")
        id("skills")
    }) {
        Div(attrs = { classes("container") }) {
            SectionHeader("Skills & Expertise", centered = true)
            Div(attrs = { classes("grid", "skills-grid") }) {
                data.skillCategories.forEachIndexed { i, category ->
                    Div(attrs = {
                        classes("glass", "card", "skill-card", "reveal")
                        style { property("--reveal-delay", "${i * 80}ms") }
                    }) {
                        Div(attrs = {
                            classes("skill-header", "skill-header-shimmer")
                            style { property("background", skillGradient(category.icon)) }
                        }) {
                            Div(attrs = { classes("skill-header-icon") }) {
                                Span(attrs = { classes("material-symbols-outlined", "skill-header-mat-icon") }) {
                                    Text(materialIconName(category.icon))
                                }
                            }
                            Div(attrs = { classes("skill-header-text") }) {
                                H3 { Text(category.name) }
                                P { Text(skillTagline(category.icon)) }
                            }
                        }
                        category.skills.forEach { skill ->
                            Div(attrs = { classes("skill-row") }) {
                                Div(attrs = { classes("skill-head") }) {
                                    Span { Text(skill.name) }
                                    Span(attrs = { classes("skill-pct") }) { Text("${skill.proficiency}%") }
                                }
                                Div(attrs = { classes("skill-track") }) {
                                    Div(attrs = {
                                        classes("skill-fill")
                                        style { property("--skill-width", "${skill.proficiency}%") }
                                    }) {}
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExperienceSection(data: PortfolioData) {
    Section(attrs = {
        classes("section", "reveal")
        id("experience")
    }) {
        Div(attrs = { classes("container") }) {
            SectionHeader("Work Experience", centered = true)
            Div(attrs = { classes("stack") }) {
                data.experience.forEachIndexed { i, exp ->
                    Article(attrs = {
                        classes("glass", "card", "experience-card", "reveal")
                        style { property("--reveal-delay", "${i * 150}ms") }
                    }) {
                        Div(attrs = { classes("exp-header") }) {
                            Div {
                                H3 { Text(exp.role) }
                                P(attrs = { classes("company") }) { Text(exp.company) }
                                P(attrs = { classes("muted", "small") }) { Text("${exp.location} · ${exp.period}") }
                            }
                            exp.link?.let { link ->
                                A(href = link, attrs = {
                                    classes("icon-link")
                                    attr("target", "_blank")
                                    attr("rel", "noopener noreferrer")
                                    attr("aria-label", "Open link")
                                }) { Text("↗") }
                            }
                        }
                        Ul(attrs = { classes("bullet-list", "compact") }) {
                            exp.highlights.forEach { h ->
                                Li { Text(h) }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrganizationsSection(data: PortfolioData) {
    Section(attrs = {
        classes("section", "reveal")
        id("organizations")
    }) {
        Div(attrs = { classes("container") }) {
            SectionHeader("Helping Organisations Build Apps", centered = true)
            P(attrs = { classes("section-subtitle", "center") }) {
                Text("Partnering with teams and institutions to ship production-ready mobile products.")
            }
            Div(attrs = { classes("grid", "org-grid") }) {
                data.clientOrganizations.forEachIndexed { i, org ->
                    Article(attrs = {
                        classes("glass", "card", "org-card", "reveal")
                        style { property("--reveal-delay", "${i * 120}ms") }
                    }) {
                        Img(src = imageSrc(org.imageKey), attrs = {
                            classes("card-image", "org-image")
                            attr("alt", org.appName)
                        })
                        P(attrs = { classes("company") }) { Text(org.name) }
                        H3 { Text(org.appName) }
                        P(attrs = { classes("muted") }) { Text(org.description) }
                        org.link?.let { link ->
                            A(href = link, attrs = {
                                classes("btn", "btn-outline", "btn-sm")
                                attr("target", "_blank")
                                attr("rel", "noopener noreferrer")
                            }) { Text("View app") }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectsSection(data: PortfolioData) {
    Section(attrs = {
        classes("section", "reveal")
        id("projects")
    }) {
        Div(attrs = { classes("container") }) {
            SectionHeader("Featured Projects", centered = true)
            Div(attrs = { classes("grid", "project-grid") }) {
                data.projects.forEachIndexed { i, project ->
                    Article(attrs = {
                        classes("glass", "card", "project-card", "reveal")
                        style { property("--reveal-delay", "${i * 120}ms") }
                    }) {
                        Img(src = imageSrc(project.imageKey), attrs = {
                            classes("card-image")
                            attr("alt", project.name)
                        })
                        H3 { Text(project.name) }
                        P(attrs = { classes("muted") }) { Text(project.description) }
                        if (project.highlights.isNotEmpty()) {
                            Ul(attrs = { classes("bullet-list", "compact") }) {
                                project.highlights.take(2).forEach { h ->
                                    Li { Text(h) }
                                }
                            }
                        }
                        Div(attrs = { classes("chips") }) {
                            project.technologies.take(4).forEach { tech ->
                                Span(attrs = { classes("chip") }) { Text(tech) }
                            }
                        }
                        Div(attrs = { classes("card-actions") }) {
                            project.githubUrl?.let { url ->
                                A(href = url, attrs = {
                                    classes("btn", "btn-outline", "btn-sm")
                                    attr("target", "_blank")
                                    attr("rel", "noopener noreferrer")
                                }) { Text("GitHub") }
                            }
                            project.liveUrl?.let { url ->
                                A(href = url, attrs = {
                                    classes("btn", "btn-outline", "btn-sm")
                                    attr("target", "_blank")
                                    attr("rel", "noopener noreferrer")
                                }) { Text("Live") }
                            }
                            project.demoUrl?.let { url ->
                                A(href = url, attrs = {
                                    classes("btn", "btn-outline", "btn-sm")
                                    attr("target", "_blank")
                                    attr("rel", "noopener noreferrer")
                                }) { Text("Demo") }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ServicesSection(data: PortfolioData) {
    Section(attrs = {
        classes("section", "reveal")
        id("services")
    }) {
        Div(attrs = { classes("container") }) {
            SectionHeader("Freelancing Services", centered = true)
            P(attrs = { classes("section-subtitle", "center") }) {
                Text("Select a service to explore — the featured panel updates automatically.")
            }

            Div(attrs = { classes("service-stats", "glass", "reveal") }) {
                Div(attrs = { classes("service-stat") }) {
                    Strong { Text("${data.freelanceServices.size}") }
                    Span { Text("Services") }
                }
                Div(attrs = { classes("service-stat") }) {
                    Strong { Text("50+") }
                    Span { Text("Projects") }
                }
                Div(attrs = { classes("service-stat") }) {
                    Strong { Text("100K+") }
                    Span { Text("Downloads") }
                }
                Div(attrs = { classes("service-stat") }) {
                    Strong { Text("24h") }
                    Span { Text("Response") }
                }
            }

            Div(attrs = { classes("service-chips", "reveal") }) {
                data.freelanceServices.forEachIndexed { index, service ->
                    Button(attrs = {
                        classes(
                            "chip",
                            "service-chip",
                            *(if (index == 0) arrayOf("active") else emptyArray()),
                        )
                        attr("type", "button")
                        attr("data-service-index", index.toString())
                    }) {
                        Span(attrs = {
                            classes("service-chip-icon-wrap")
                            style { property("background", serviceGradient(service.icon)) }
                        }) {
                            Span(attrs = { classes("material-symbols-outlined", "service-chip-icon") }) {
                                Text(materialIconName(service.icon))
                            }
                        }
                        Text(service.title)
                    }
                }
            }

            Div(attrs = { classes("service-panels-wrap", "reveal") }) {
                data.freelanceServices.forEachIndexed { index, service ->
                    Article(attrs = {
                        classes(
                            "glass",
                            "card",
                            "service-panel",
                            *(if (index == 0) arrayOf("active") else emptyArray()),
                        )
                        attr("data-service-panel", index.toString())
                    }) {
                        Div(attrs = { classes("featured-panel") }) {
                            Div(attrs = {
                                classes("featured-icon")
                                style { property("background", serviceGradient(service.icon)) }
                            }) {
                                Span(attrs = { classes("material-symbols-outlined", "featured-mat-icon") }) {
                                    Text(materialIconName(service.icon))
                                }
                            }
                            Div {
                                P(attrs = { classes("featured-label") }) { Text("Featured Service") }
                                H3 { Text(service.title) }
                                P(attrs = { classes("muted") }) { Text(service.description) }
                                if (service.deliveryHint.isNotBlank()) {
                                    P(attrs = { classes("delivery-hint") }) { Text(service.deliveryHint) }
                                }
                                Div(attrs = { classes("chips") }) {
                                    service.tags.forEach { tag ->
                                        Span(attrs = { classes("chip") }) { Text(tag) }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Div(attrs = { classes("service-grid") }) {
                data.freelanceServices.forEachIndexed { index, service ->
                    val preview = buildString {
                        append(service.description.take(90))
                        if (service.description.length > 90) append('…')
                    }
                    Article(attrs = {
                        classes(
                            "glass",
                            "card",
                            "service-grid-card",
                            "reveal",
                            *(if (index == 0) arrayOf("selected") else emptyArray()),
                        )
                        style {
                            property("--reveal-delay", "${index * 80}ms")
                            property("--service-accent", serviceAccentColor(service.icon))
                        }
                    }) {
                        Div(attrs = { classes("service-grid-head") }) {
                            Div(attrs = {
                                classes("service-grid-icon-wrap")
                                style { property("background", serviceGradient(service.icon)) }
                            }) {
                                Span(attrs = { classes("material-symbols-outlined", "service-grid-mat-icon") }) {
                                    Text(materialIconName(service.icon))
                                }
                            }
                            Div(attrs = { classes("service-grid-title-wrap") }) {
                                Strong { Text(service.title) }
                                if (service.deliveryHint.isNotBlank()) {
                                    P(attrs = { classes("delivery-hint", "small") }) { Text(service.deliveryHint) }
                                }
                            }
                            Span(attrs = { classes("material-symbols-outlined", "service-grid-check") }) {
                                Text("check_circle")
                            }
                        }
                        P(attrs = { classes("muted", "small") }) { Text(preview) }
                    }
                }
            }
        }
    }
}

@Composable
fun TestimonialsSection(data: PortfolioData) {
    Section(attrs = {
        classes("section", "reveal")
        id("testimonials")
    }) {
        Div(attrs = { classes("container") }) {
            SectionHeader("Client Testimonials", centered = true)
            P(attrs = { classes("section-subtitle", "center") }) {
                Text("What clients and collaborators say about working together.")
            }
            Div(attrs = { classes("grid", "testimonial-grid") }) {
                data.testimonials.forEachIndexed { i, t ->
                    Div(attrs = {
                        classes("glass", "card", "testimonial-card", "reveal")
                        style { property("--reveal-delay", "${i * 100}ms") }
                    }) {
                        Div(attrs = { classes("stars") }) {
                            repeat(t.rating) {
                                Span(attrs = { classes("star") }) { Text("★") }
                            }
                            if (t.isPlaceholder) {
                                Span(attrs = { classes("placeholder-badge") }) { Text("Sample") }
                            }
                        }
                        P(attrs = { classes("quote") }) { Text("\"${t.content}\"") }
                        P(attrs = { classes("testimonial-author") }) {
                            Strong { Text(t.clientName) }
                            Span(attrs = { classes("muted") }) { Text(" — ${t.role}, ${t.company}") }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContactSection(data: PortfolioData) {
    val info = data.personalInfo
    Section(attrs = {
        classes("section", "reveal")
        id("contact")
    }) {
        Div(attrs = { classes("container") }) {
            SectionHeader("Get In Touch", centered = true)
            P(attrs = { classes("section-subtitle", "center") }) {
                Text("Recruiters & clients — reach out directly. I typically respond within 24 hours.")
            }

            Div(attrs = { classes("contact-grid") }) {
                Div(attrs = { classes("glass", "card", "contact-info", "reveal") }) {
                    H3 { Text("Let's work together") }
                    P(attrs = { classes("muted") }) {
                        Text("Recruiters & clients — reach out directly. I typically respond within 24 hours.")
                    }
                    P {
                        A(href = gmailWebComposeUrl(info.email), attrs = {
                            attr("target", "_blank")
                            attr("rel", "noopener noreferrer")
                        }) { Text(info.email) }
                    }
                    P { A(href = "tel:${info.phone}") { Text(info.phone) } }
                    P(attrs = { classes("muted") }) { Text(info.location) }

                    H4 { Text("Social Links") }
                    Div(attrs = { classes("social-grid") }) {
                        data.socialLinks.forEach { link ->
                            val icon = socialIconSrc(link.icon)
                            A(href = emailLinkUrl(link.url), attrs = {
                                classes("social-card", "glass")
                                attr("target", "_blank")
                                attr("rel", "noopener noreferrer")
                            }) {
                                if (icon != null) {
                                    Img(src = icon, attrs = {
                                        classes("social-icon")
                                        attr("alt", link.name)
                                    })
                                }
                                Span { Text(link.name) }
                            }
                        }
                    }
                }

                Form(attrs = {
                    classes("glass", "card", "contact-form", "reveal")
                    id("contact-form")
                    attr("data-email", info.email)
                }) {
                    H3 { Text("Send a Message") }
                    Label {
                        Text("Name")
                        Input(type = InputType.Text, attrs = {
                            attr("name", "name")
                            attr("required", "true")
                            attr("placeholder", "Your name")
                        })
                    }
                    Label {
                        Text("Subject")
                        Input(type = InputType.Text, attrs = {
                            attr("name", "subject")
                            attr("required", "true")
                            attr("placeholder", "Project or role")
                        })
                    }
                    Label {
                        Text("Message")
                        TextArea(attrs = {
                            attr("name", "message")
                            attr("required", "true")
                            attr("placeholder", "Tell me about your project...")
                            attr("rows", "5")
                            attr("cols", "10")
                        })
                    }
                    P(attrs = {
                        classes("form-error", "hidden")
                        id("form-error")
                    }) {}
                    Button(attrs = {
                        classes("btn", "btn-gradient")
                        attr("type", "submit")
                    }) { Text("Send via Email") }
                }
            }
        }
    }
}

@Composable
fun FooterSection(data: PortfolioData) {
    val info = data.personalInfo
    val footerLinks = listOf(
        Triple("Resume", "resume", info.resumeUrl),
        Triple("LinkedIn", "linkedin", info.linkedIn),
        Triple("GitHub", "github", info.github),
        Triple("Topmate", "topmate", "https://topmate.io/shashank_ranjan10"),
        Triple("Email", "email", gmailWebComposeUrl(info.email)),
    )

    Footer(attrs = { classes("site-footer") }) {
        Div(attrs = { classes("container", "footer-inner") }) {
            H3(attrs = { classes("footer-name") }) { Text(info.name) }
            P(attrs = { classes("footer-title") }) { Text(info.title) }

            H4(attrs = { classes("footer-connect") }) { Text("Connect with me") }

            Div(attrs = { classes("footer-social-row") }) {
                footerLinks.forEach { (label, iconKey, url) ->
                    val iconSrc = socialIconSrc(iconKey)
                    A(href = url, attrs = {
                        classes("footer-social-card")
                        attr("target", "_blank")
                        attr("rel", "noopener noreferrer")
                    }) {
                        Span(attrs = { classes("footer-social-icon-wrap") }) {
                            if (iconSrc != null) {
                                Img(src = iconSrc, attrs = {
                                    classes("footer-social-icon")
                                    attr("alt", label)
                                })
                            }
                        }
                        Span(attrs = { classes("footer-social-label") }) { Text(label) }
                    }
                }
            }

            Hr(attrs = { classes("footer-divider") })

            P(attrs = { classes("footer-copyright") }) {
                Text("© 2026 ${info.name}. All rights reserved.")
            }
            P(attrs = { classes("footer-built") }) {
                Text("Built with Kobweb · Kotlin Compose HTML")
            }
        }
    }
}

@Composable
fun SiteHeader() {
    Header(attrs = { classes("site-header") }) {
        Div(attrs = { classes("container", "nav-inner") }) {
            A(href = "#home", attrs = { classes("nav-logo") }) { Text("SR") }
            Nav(attrs = { classes("desktop-nav") }) {
                navSections.forEach { (id, label) ->
                    A(href = "#$id", attrs = { classes("nav-link") }) { Text(label) }
                }
            }
            Div(attrs = { classes("nav-actions") }) {
                Div(attrs = { classes("theme-picker") }) {
                    Button(attrs = {
                        classes("theme-chip")
                        attr("type", "button")
                        id("theme-chip")
                    }) {
                        Span(attrs = { classes("theme-dot") }) {}
                        Span(attrs = { id("theme-label") }) { Text("Midnight") }
                    }
                    Div(attrs = {
                        classes("theme-menu")
                        id("theme-menu")
                    }) {}
                }
                A(href = "#contact", attrs = { classes("btn", "btn-gradient", "btn-sm") }) { Text("Hire Me") }
                Button(attrs = {
                    classes("menu-toggle")
                    attr("type", "button")
                    attr("aria-label", "Open menu")
                }) {
                    Span(attrs = { classes("menu-bar") }) {}
                    Span(attrs = { classes("menu-bar") }) {}
                    Span(attrs = { classes("menu-bar") }) {}
                }
            }
        }
        Nav(attrs = { classes("mobile-nav") }) {
            A(href = "#home", attrs = { classes("nav-link") }) { Text("Home") }
            navSections.forEach { (id, label) ->
                A(href = "#$id", attrs = { classes("nav-link") }) { Text(label) }
            }
        }
    }
}

@Composable
fun FloatingFab() {
    A(href = "#contact", attrs = {
        classes("fab")
        attr("title", "Contact")
    }) {
        Span(attrs = { classes("label") }) { Text("Let's Talk") }
    }
}

@Composable
private fun Strong(content: @Composable () -> Unit) {
    B { content() }
}
