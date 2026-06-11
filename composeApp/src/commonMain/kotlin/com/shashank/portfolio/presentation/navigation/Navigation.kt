package com.shashank.portfolio.presentation.navigation

/**
 * Navigation destinations for the single-page portfolio.
 * Each section has an ID used for smooth scroll navigation.
 */
enum class PortfolioSection(val id: String, val label: String) {
    Home("home", "Home"),
    About("about", "About"),
    Skills("skills", "Skills"),
    Experience("experience", "Experience"),
    Projects("projects", "Projects"),
    Services("services", "Services"),
    Testimonials("testimonials", "Testimonials"),
    Contact("contact", "Contact"),
}
