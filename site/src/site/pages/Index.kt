package com.shashank.portfolio.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.shashank.portfolio.components.PortfolioShell
import com.shashank.portfolio.components.sections.*
import com.shashank.portfolio.data.source.PortfolioDataSource
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.dom.Main

@Page
@Composable
fun HomePage() {
    val data = remember { PortfolioDataSource.getPortfolioData() }

    PortfolioShell {
        SiteHeader()
        Main(attrs = { classes("site-main") }) {
            HeroSection(data)
            AboutSection(data)
            SkillsSection(data)
            ExperienceSection(data)
            OrganizationsSection(data)
            ProjectsSection(data)
            ServicesSection(data)
            TestimonialsSection(data)
            ContactSection(data)
        }
        FooterSection(data)
        FloatingFab()
    }
}
