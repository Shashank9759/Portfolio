package com.shashank.portfolio.domain.model

import kotlinx.serialization.Serializable

@Serializable
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

@Serializable
data class Stat(
    val label: String,
    val value: Int,
    val suffix: String = "",
    val showStarIcon: Boolean = false,
)

@Serializable
data class Education(
    val institution: String,
    val degree: String,
    val location: String,
    val graduationDate: String,
)

@Serializable
data class Experience(
    val company: String,
    val role: String,
    val location: String,
    val period: String,
    val link: String? = null,
    val highlights: List<String>,
)

@Serializable
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

@Serializable
data class SkillCategory(
    val name: String,
    val icon: String,
    val skills: List<Skill>,
)

@Serializable
data class Skill(
    val name: String,
    val proficiency: Int,
)

@Serializable
data class FreelanceService(
    val title: String,
    val description: String,
    val icon: String,
    val tags: List<String> = emptyList(),
    val deliveryHint: String = "",
)

@Serializable
data class ClientOrganization(
    val name: String,
    val appName: String,
    val description: String,
    val imageKey: String,
    val link: String? = null,
)

@Serializable
data class Testimonial(
    val clientName: String,
    val role: String,
    val company: String,
    val content: String,
    val rating: Int,
    val isPlaceholder: Boolean = false,
)

@Serializable
data class SocialLink(
    val name: String,
    val url: String,
    val icon: String,
)

@Serializable
data class PortfolioData(
    val personalInfo: PersonalInfo,
    val stats: List<Stat>,
    val aboutSummary: String,
    val careerHighlights: List<String>,
    val education: List<Education>,
    val experience: List<Experience>,
    val clientOrganizations: List<ClientOrganization>,
    val projects: List<Project>,
    val skillCategories: List<SkillCategory>,
    val freelanceServices: List<FreelanceService>,
    val testimonials: List<Testimonial>,
    val socialLinks: List<SocialLink>,
)

@Serializable
data class PortfolioMeta(
    val version: Int,
    val updatedAtEpochMs: Long,
    val source: String = "ktor-server",
)

@Serializable
data class PortfolioResponse(
    val meta: PortfolioMeta,
    val data: PortfolioData,
)
