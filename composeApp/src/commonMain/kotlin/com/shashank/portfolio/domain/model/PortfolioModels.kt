package com.shashank.portfolio.domain.model

/**
 * Core domain models representing portfolio content.
 * These models are platform-agnostic and used across all presentation layers.
 */

data class PersonalInfo(
    val name: String,
    val title: String,
    val summary: String,
    val location: String,
    val email: String,
    val phone: String,
    val linkedIn: String,
    val github: String,
    val whatsapp: String,
    val resumeUrl: String,
)

data class Stat(
    val label: String,
    val value: Int,
    val suffix: String = "",
    /** Renders a Material star icon instead of unicode (Wasm-safe). */
    val showStarIcon: Boolean = false,
)

data class Education(
    val institution: String,
    val degree: String,
    val location: String,
    val graduationDate: String,
)

data class Experience(
    val company: String,
    val role: String,
    val location: String,
    val period: String,
    val link: String? = null,
    val highlights: List<String>,
)

data class Project(
    val name: String,
    val description: String,
    val technologies: List<String>,
    val imageKey: String,
    val githubUrl: String? = null,
    val liveUrl: String? = null,
    val demoUrl: String? = null,
    val highlights: List<String> = emptyList(),
)

data class SkillCategory(
    val name: String,
    val icon: String,
    val skills: List<Skill>,
)

data class Skill(
    val name: String,
    val proficiency: Int, // 0-100
)

data class FreelanceService(
    val title: String,
    val description: String,
    val icon: String,
)

data class Testimonial(
    val clientName: String,
    val role: String,
    val company: String,
    val content: String,
    val rating: Int,
    val isPlaceholder: Boolean = false,
)

data class SocialLink(
    val name: String,
    val url: String,
    val icon: String,
)

data class PortfolioData(
    val personalInfo: PersonalInfo,
    val stats: List<Stat>,
    val aboutSummary: String,
    val careerHighlights: List<String>,
    val education: List<Education>,
    val experience: List<Experience>,
    val projects: List<Project>,
    val skillCategories: List<SkillCategory>,
    val freelanceServices: List<FreelanceService>,
    val testimonials: List<Testimonial>,
    val socialLinks: List<SocialLink>,
)
