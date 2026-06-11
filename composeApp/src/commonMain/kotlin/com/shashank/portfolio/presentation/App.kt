package com.shashank.portfolio.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.shashank.portfolio.presentation.components.AndroidDevBackground
import com.shashank.portfolio.presentation.components.ResponsiveNavigationBar
import com.shashank.portfolio.presentation.components.SectionContentBackdrop
import com.shashank.portfolio.presentation.components.onVisibilityChanged
import com.shashank.portfolio.presentation.components.rememberVisibilityState
import com.shashank.portfolio.presentation.navigation.PortfolioSection
import com.shashank.portfolio.presentation.navigation.rememberSectionScrollRegistry
import com.shashank.portfolio.presentation.navigation.sectionAnchor
import com.shashank.portfolio.presentation.screens.*
import com.shashank.portfolio.presentation.theme.Layout
import com.shashank.portfolio.presentation.theme.PortfolioTheme
import com.shashank.portfolio.presentation.viewmodel.PortfolioViewModel

@Composable
fun App() {
    val viewModel = remember { PortfolioViewModel() }
    val data = viewModel.portfolioData
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val sectionRegistry = rememberSectionScrollRegistry()
    val density = LocalDensity.current
    val navBarOffsetPx = with(density) { Layout.navHeight.roundToPx() }

    val heroVisibility = rememberVisibilityState()
    val aboutVisibility = rememberVisibilityState()
    val skillsVisibility = rememberVisibilityState()
    val experienceVisibility = rememberVisibilityState()
    val projectsVisibility = rememberVisibilityState()
    val servicesVisibility = rememberVisibilityState()
    val testimonialsVisibility = rememberVisibilityState()
    val contactVisibility = rememberVisibilityState()

    LaunchedEffect(Unit) { heroVisibility.isVisible = true }

    fun navigateTo(section: PortfolioSection) {
        sectionRegistry.scrollToSection(
            section = section,
            scrollState = scrollState,
            coroutineScope = coroutineScope,
            navBarOffsetPx = navBarOffsetPx,
        )
    }

    PortfolioTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Parallax edge decorations — scrollOffset drives subtle movement
                AndroidDevBackground(
                    modifier = Modifier.matchParentSize(),
                    scrollOffset = scrollState.value,
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics { contentDescription = "Shashank Ranjan Portfolio" },
                ) {
                    ResponsiveNavigationBar(onNavigate = ::navigateTo)

                    Box(modifier = Modifier.fillMaxSize()) {
                        // Subtle backdrop over scroll content
                        SectionContentBackdrop(modifier = Modifier.matchParentSize())

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState),
                        ) {
                            HeroSection(
                                personalInfo = data.personalInfo,
                                stats = data.stats,
                                isVisible = heroVisibility.isVisible,
                                onNavigate = ::navigateTo,
                                modifier = Modifier
                                    .sectionAnchor(PortfolioSection.Home, sectionRegistry)
                                    .onVisibilityChanged(heroVisibility),
                            )

                            AboutSection(
                                summary = data.aboutSummary,
                                highlights = data.careerHighlights,
                                education = data.education,
                                isVisible = aboutVisibility.isVisible,
                                modifier = Modifier
                                    .sectionAnchor(PortfolioSection.About, sectionRegistry)
                                    .onVisibilityChanged(aboutVisibility),
                            )

                            SkillsSection(
                                categories = data.skillCategories,
                                isVisible = skillsVisibility.isVisible,
                                modifier = Modifier
                                    .sectionAnchor(PortfolioSection.Skills, sectionRegistry)
                                    .onVisibilityChanged(skillsVisibility),
                            )

                            ExperienceSection(
                                experiences = data.experience,
                                isVisible = experienceVisibility.isVisible,
                                modifier = Modifier
                                    .sectionAnchor(PortfolioSection.Experience, sectionRegistry)
                                    .onVisibilityChanged(experienceVisibility),
                            )

                            ProjectsSection(
                                projects = data.projects,
                                isVisible = projectsVisibility.isVisible,
                                modifier = Modifier
                                    .sectionAnchor(PortfolioSection.Projects, sectionRegistry)
                                    .onVisibilityChanged(projectsVisibility),
                            )

                            ServicesSection(
                                services = data.freelanceServices,
                                isVisible = servicesVisibility.isVisible,
                                modifier = Modifier
                                    .sectionAnchor(PortfolioSection.Services, sectionRegistry)
                                    .onVisibilityChanged(servicesVisibility),
                            )

                            TestimonialsSection(
                                testimonials = data.testimonials,
                                isVisible = testimonialsVisibility.isVisible,
                                modifier = Modifier
                                    .sectionAnchor(PortfolioSection.Testimonials, sectionRegistry)
                                    .onVisibilityChanged(testimonialsVisibility),
                            )

                            ContactSection(
                                personalInfo = data.personalInfo,
                                socialLinks = data.socialLinks,
                                viewModel = viewModel,
                                isVisible = contactVisibility.isVisible,
                                modifier = Modifier
                                    .sectionAnchor(PortfolioSection.Contact, sectionRegistry)
                                    .onVisibilityChanged(contactVisibility),
                            )

                            FooterSection(personalInfo = data.personalInfo)
                        }
                    }
                }
            }
        }
    }
}
