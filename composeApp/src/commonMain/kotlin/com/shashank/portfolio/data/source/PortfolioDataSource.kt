package com.shashank.portfolio.data.source

import com.shashank.portfolio.domain.model.*

/**
 * Static data source containing all portfolio content derived from Shashank Ranjan's resume.
 * In a production app, this could be replaced with a remote API or CMS.
 */
object PortfolioDataSource {

    fun getPortfolioData(): PortfolioData = PortfolioData(
        personalInfo = PersonalInfo(
            name = "Shashank Ranjan",
            title = "SDE-1 · Android & Cross-Platform Mobile Engineer",
            summary = "I build production-grade apps for Android Mobile, Android TV (Leanback), and cross-platform stacks — " +
                "from real-time platforms serving 100K+ users to ML-powered health solutions. Experienced in Kotlin, Compose, iOS (SwiftUI & UIKit), and TV-optimized UIs.",
            location = "Bijnor, Uttar Pradesh, India",
            email = "shashankranjantech@gmail.com",
            phone = "+919927904424",
            linkedIn = "https://www.linkedin.com/in/shashank142004/",
            github = "https://github.com/Shashank9759/",
            whatsapp = "https://wa.me/9927904424",
            resumeUrl = "https://drive.google.com/file/d/1GKQkZOtrKGyvd9rDtuEnEmYtJ6i5klmS/view?usp=sharing",
        ),
        stats = listOf(
            Stat("App Downloads", 100, "K+"),
            Stat("Projects Shipped", 10, "+"),
            Stat("Years Experience", 2, "+"),
            Stat("Play Store Rating", 4, ".6", showStarIcon = true),
        ),
        aboutSummary = "I'm a Software Development Engineer with deep expertise in native Android development — " +
            "including Android Mobile and Android TV apps using the Leanback library for immersive 10-foot UI experiences. " +
            "I also work across iOS (SwiftUI & UIKit), Kotlin Multiplatform, Compose Multiplatform, React Native, and Flutter " +
            "to deliver performant, user-centric applications with clean architecture and robust testing.",
        careerHighlights = listOf(
            "Scaled CricRadio to 100K+ users with real-time socket-based cricket scoring",
            "Achieved 95% data compression and 10% speed boost through custom server strategies",
            "Collaborated with Bristol University on Federated Learning for mental well-being detection",
            "Built ML-powered SkinLens app with custom TensorFlow Lite models",
            "Mentored interns and led technical evaluations at Lifease Solutions",
        ),
        education = listOf(
            Education(
                institution = "Kunwar Satya Vira College of Engineering and Management",
                degree = "Bachelor of Technology (B.Tech) in Computer Science",
                location = "Bijnor, Uttar Pradesh",
                graduationDate = "April 2025",
            ),
        ),
        experience = listOf(
            Experience(
                company = "Lifease Solutions LLM",
                role = "SDE-1 - Android",
                location = "Noida, Uttar Pradesh, India",
                period = "June 2025 – Present",
                link = "https://play.google.com/store/apps/details?id=com.lifease.cricradio",
                highlights = listOf(
                    "Developed CricRadio, a real-time cricket app using Kotlin, Jetpack Compose, Ktor, and Socket.IO, scaling to 100K+ users",
                    "Implemented 95% data compression and 10% speed boost via custom server strategies",
                    "Led feature optimization, UI/backend collaboration, and tech strategy based on competitive benchmarking",
                    "Wrote robust tests (JUnit, MockK, Espresso) and managed intern hiring through technical evaluations",
                ),
            ),
            Experience(
                company = "Bristol University",
                role = "Android Research Collaborator",
                location = "Bristol, United Kingdom",
                period = "Nov 2024 – May 2025",
                link = "https://drive.google.com/file/d/1TIO-CVvhHMu8bwdtO9xf8-nm46C8p0SZ/view",
                highlights = listOf(
                    "Collaborated on Federated Learning solutions for early mental well-being detection",
                    "Developed digital health app tracking physical activities via Activity Recognition, Google Fit, Sleep API",
                    "Built offline-first system using Room DB syncing to Firebase with Jetpack Compose UI",
                ),
            ),
            Experience(
                company = "Putatoe Solution Pvt. Ltd",
                role = "SDE 1 Intern",
                location = "Gorakhpur, Uttar Pradesh, India",
                period = "Feb 2024 – May 2024",
                link = "https://drive.google.com/file/d/1MOH_qfeNduzcvWoMyb-zAAlaTueUZ2u-/view",
                highlights = listOf(
                    "Implemented Agile methodologies and CI/CD using Retrofit, JSON, and WorkManager",
                    "Applied MVVM and Clean Architecture with Broadcast Receivers and Services",
                    "Developed product adding system with push notifications and Google Maps API integration",
                ),
            ),
            Experience(
                company = "Flexo Technology",
                role = "Android Developer Intern",
                location = "Maharashtra, India",
                period = "Nov 2023 – Jan 2024",
                link = "https://drive.google.com/file/aad/1VnvHppepRtSJMoOdiczbVBMp806QiIuK/view",
                highlights = listOf(
                    "Redesigned UI and added features for crypto news and status apps, improving user retention",
                    "Optimized code, resolved bugs, and enhanced UI/UX with custom bottom bar and view graphs",
                ),
            ),
        ),
        projects = listOf(
            Project(
                name = "CricRadio",
                description = "Real-time cricket score app with socket-based live updates, serving 100K+ users on the Play Store.",
                technologies = listOf("Kotlin", "Jetpack Compose", "MVVM", "Socket.IO", "Ktor", "JUnit", "MockK"),
                imageKey = "cricradio",
                liveUrl = "https://play.google.com/store/apps/details?id=com.lifease.cricradio",
                highlights = listOf(
                    "Real-time scoring via socket communication",
                    "Unit & instrumentation tests with Kover coverage",
                    "Custom pagination, caching, and Compose animations",
                ),
            ),
            Project(
                name = "CodersAidHub",
                description = "Social media platform for coders with infinite scroll feed, messaging, and AI image processing.",
                technologies = listOf("Kotlin", "Firebase", "Firestore", "Coroutines", "ML Kit", "Animations"),
                imageKey = "codersaidhub",
                githubUrl = "https://github.com/Shashank9759/CodersAidHub",
                demoUrl = "https://www.youtube.com/watch?v=f6jp2JPFvcg",
            ),
            Project(
                name = "RRBMU Studies",
                description = "Academic app for Rajasthan university with 1000+ downloads and 4.6★ rating on Play Store.",
                technologies = listOf("Kotlin", "Firebase", "AdMob", "Push Notifications", "XML"),
                imageKey = "rrbmustudies",
                githubUrl = "https://github.com/Shashank9759/RRBMU-Studies-University-App",
                liveUrl = "https://play.google.com/store/apps/details?id=com.studies.rrbmustudies",
                demoUrl = "https://www.youtube.com/shorts/5Hq4sOf_sIk",
            ),
            Project(
                name = "SkinLens",
                description = "ML-powered skin disease detection app with custom TensorFlow Lite model and CameraX integration.",
                technologies = listOf("Jetpack Compose", "Clean Architecture", "TensorFlow", "TFLite", "CameraX"),
                imageKey = "skinlens",
                githubUrl = "https://github.com/Shashank9759/SkinLens--Skin-Disease-Detector-App",
                demoUrl = "https://www.youtube.com/shorts/loplQYV1rkQ",
            ),
            Project(
                name = "Yaad Diary",
                description = "Note-taking app with Room DB, MVVM, search, and priority settings.",
                technologies = listOf("Kotlin", "Room DB", "MVVM", "Coroutines"),
                imageKey = "yaaddiary",
                githubUrl = "https://github.com/Shashank9759/Yaad-Diary",
                demoUrl = "https://www.youtube.com/shorts/fxOPU7HlgpU",
            ),
            Project(
                name = "College Registration",
                description = "Real-time student registration app built with Hilt, MVVM, and Firebase Realtime DB.",
                technologies = listOf("Kotlin", "Hilt", "MVVM", "Firebase Realtime DB"),
                imageKey = "collegereg",
                githubUrl = "https://github.com/Shashank9759/College-Registration-App",
                demoUrl = "https://www.youtube.com/shorts/n1GgbkVmPho",
            ),
        ),
        skillCategories = listOf(
            SkillCategory(
                name = "Android Mobile",
                icon = "android",
                skills = listOf(
                    Skill("Jetpack Compose", 95),
                    Skill("Kotlin", 95),
                    Skill("MVVM / Clean Architecture", 90),
                    Skill("Room / SQLite", 85),
                    Skill("Testing (JUnit, Espresso, MockK)", 85),
                ),
            ),
            SkillCategory(
                name = "Android TV & Leanback",
                icon = "tv",
                skills = listOf(
                    Skill("Leanback Library", 80),
                    Skill("RowsSupportFragment", 75),
                    Skill("D-pad / Focus Navigation", 80),
                    Skill("TV-Optimized UI (10-foot)", 78),
                    Skill("Android TV Compose", 72),
                ),
            ),
            SkillCategory(
                name = "Kotlin Multiplatform",
                icon = "kmp",
                skills = listOf(
                    Skill("Shared Business Logic", 80),
                    Skill("Ktor Client", 75),
                    Skill("SQLDelight", 70),
                    Skill("KMP Networking", 75),
                ),
            ),
            SkillCategory(
                name = "Compose Multiplatform",
                icon = "cmp",
                skills = listOf(
                    Skill("Shared UI Components", 85),
                    Skill("Web / Wasm Target", 80),
                    Skill("Responsive Layouts", 85),
                    Skill("Material 3 Theming", 90),
                ),
            ),
            SkillCategory(
                name = "iOS Development",
                icon = "ios",
                skills = listOf(
                    Skill("SwiftUI", 75),
                    Skill("UIKit", 70),
                    Skill("Auto Layout & Storyboards", 65),
                    Skill("iOS Architecture Patterns", 70),
                ),
            ),
            SkillCategory(
                name = "Cross-Platform",
                icon = "cross",
                skills = listOf(
                    Skill("React Native", 70),
                    Skill("Flutter", 65),
                    Skill("KMP Shared Logic", 80),
                ),
            ),
            SkillCategory(
                name = "Backend & APIs",
                icon = "api",
                skills = listOf(
                    Skill("RESTful APIs", 90),
                    Skill("Retrofit / OkHttp", 90),
                    Skill("Socket.IO / WebSockets", 85),
                    Skill("Firebase / Supabase", 85),
                ),
            ),
            SkillCategory(
                name = "Tools & Technologies",
                icon = "tools",
                skills = listOf(
                    Skill("Git / GitHub", 90),
                    Skill("Gradle / CI/CD", 85),
                    Skill("TensorFlow Lite", 80),
                    Skill("Performance Optimization", 85),
                ),
            ),
        ),
        freelanceServices = listOf(
            FreelanceService("Android App Development", "End-to-end native Android mobile apps with Jetpack Compose, MVVM, and Clean Architecture.", "android"),
            FreelanceService("Android TV Development", "Android TV apps with Leanback library, D-pad navigation, and immersive 10-foot UI experiences.", "tv"),
            FreelanceService("Kotlin Multiplatform Development", "Shared business logic across Android, iOS, and Desktop with KMP.", "kmp"),
            FreelanceService("Compose Multiplatform Development", "Unified UI across platforms with Compose Multiplatform for web and mobile.", "cmp"),
            FreelanceService("Cross-Platform Mobile Development", "React Native, Flutter, SwiftUI, and UIKit solutions for multi-platform reach.", "cross"),
            FreelanceService("UI/UX Implementation", "Pixel-perfect, animated interfaces with modern design patterns and glassmorphism.", "ui"),
            FreelanceService("API Integration", "RESTful APIs, WebSockets, Firebase, and third-party SDK integrations.", "api"),
            FreelanceService("Performance Optimization", "Multithreading, caching, compression strategies, and app profiling.", "perf"),
            FreelanceService("App Maintenance & Support", "Bug fixes, feature updates, testing, and long-term app maintenance.", "support"),
        ),
        testimonials = listOf(
            Testimonial(
                clientName = "Client Name",
                role = "Product Manager",
                company = "Tech Company",
                content = "Placeholder for future client review. Shashank delivered exceptional quality work with attention to detail and timely delivery.",
                rating = 5,
                isPlaceholder = true,
            ),
            Testimonial(
                clientName = "Client Name",
                role = "Startup Founder",
                company = "Mobile Startup",
                content = "Placeholder for future client review. Outstanding Android development skills and great communication throughout the project.",
                rating = 5,
                isPlaceholder = true,
            ),
            Testimonial(
                clientName = "Client Name",
                role = "CTO",
                company = "SaaS Company",
                content = "Placeholder for future client review. Highly recommend for complex mobile app projects requiring clean architecture.",
                rating = 5,
                isPlaceholder = true,
            ),
        ),
        socialLinks = listOf(
            SocialLink("Resume", "https://drive.google.com/file/d/1GKQkZOtrKGyvd9rDtuEnEmYtJ6i5klmS/view?usp=sharing", "resume"),
            SocialLink("LinkedIn", "https://www.linkedin.com/in/shashank142004/", "linkedin"),
            SocialLink("GitHub", "https://github.com/Shashank9759/", "github"),
            SocialLink("Email", "mailto:shashankranjantech@gmail.com", "email"),
        ),
    )
}
