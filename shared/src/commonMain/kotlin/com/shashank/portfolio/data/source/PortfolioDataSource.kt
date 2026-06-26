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
            title = "Android and Multiplatform Developer",
            summary = "I build production-grade apps for Android Mobile, Android TV (Leanback), and cross-platform stacks — " +
                "from real-time platforms serving 100K+ users to ML-powered health solutions. Experienced in Kotlin, Compose, iOS (SwiftUI & UIKit), and TV-optimized UIs.",
            location = "Noida, Uttar Pradesh, India",
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
                company = "Lifease Solutions LLP",
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
                highlights = listOf(
                    "Redesigned UI and added features for crypto news and status apps, improving user retention",
                    "Optimized code, resolved bugs, and enhanced UI/UX with custom bottom bar and view graphs",
                ),
            ),
        ),
        clientOrganizations = listOf(
            ClientOrganization(
                name = "Lifease Solutions LLP",
                appName = "CricRadio",
                description = "Real-time cricket scoring app scaled to 100K+ Play Store downloads with sockets, compression, and Compose UI.",
                imageKey = "cricradio",
                link = "https://play.google.com/store/apps/details?id=com.lifease.cricradio",
            ),
            ClientOrganization(
                name = "Bristol University",
                appName = "Mental Well-being Tracker",
                description = "Research collaboration — offline-first health app with Federated Learning, Google Fit, and Jetpack Compose.",
                imageKey = "skinlens",
                link = "https://drive.google.com/file/d/1TIO-CVvhHMu8bwdtO9xf8-nm46C8p0SZ/view",
            ),
            ClientOrganization(
                name = "RRBMU College",
                appName = "RRBMU Studies",
                description = "University learning app with Firebase, push notifications, and content workflows — 1000+ student downloads.",
                imageKey = "rrbmustudies",
                link = "https://play.google.com/store/apps/details?id=com.studies.rrbmustudies",
            ),
            ClientOrganization(
                name = "Putatoe Solution Pvt. Ltd",
                appName = "Putatoe : One Solution",
                description = "One-stop local business platform — search, compare, and book services with maps, chat, and push notifications. 10K+ Play Store downloads.",
                imageKey = "putatoe",
                link = "https://play.google.com/store/apps/details?id=com.putatoeapp.application&hl=en_IN",
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
                name = "DHM3",
                description = "Bristol University research collaboration — digital health monitoring app with Federated Learning, " +
                    "Activity Recognition, Google Fit, and offline-first Room sync.",
                technologies = listOf("Kotlin", "Jetpack Compose", "Room", "Google Fit", "Federated Learning", "Firebase"),
                imageKey = "dhm3",
                githubUrl = "https://github.com/Shashank9759/DHM3",
                highlights = listOf(
                    "Android Research Collaborator at Bristol University",
                    "Offline-first health tracking with Room + Firebase sync",
                    "Activity Recognition and Google Fit integration",
                ),
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
                name = "AI & Machine Learning",
                icon = "ai",
                skills = listOf(
                    Skill("On-device ML (TensorFlow Lite)", 85),
                    Skill("ML Kit & Vision APIs", 82),
                    Skill("AI API Integration (Gemini / OpenAI)", 80),
                    Skill("Federated Learning", 75),
                    Skill("Prompt Engineering & RAG", 78),
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
            FreelanceService(
                title = "Android App Development",
                description = "End-to-end native Android mobile apps with Jetpack Compose, MVVM, and Clean Architecture.",
                icon = "android",
                tags = listOf("Jetpack Compose", "MVVM", "Room", "Play Store", "Testing"),
                deliveryHint = "Typical MVP: 4–8 weeks",
            ),
            FreelanceService(
                title = "Android TV Development",
                description = "Android TV apps with Leanback library, D-pad navigation, and immersive 10-foot UI experiences.",
                icon = "tv",
                tags = listOf("Leanback", "D-pad UX", "Focus Navigation", "TV Compose"),
                deliveryHint = "TV-ready builds & sideload support",
            ),
            FreelanceService(
                title = "Kotlin Multiplatform Development",
                description = "Shared business logic across Android, iOS, and Desktop with KMP.",
                icon = "kmp",
                tags = listOf("Ktor", "SQLDelight", "expect/actual", "Shared Domain"),
                deliveryHint = "Shared modules + platform shells",
            ),
            FreelanceService(
                title = "Compose Multiplatform Development",
                description = "Unified UI across platforms with Compose Multiplatform for web and mobile.",
                icon = "cmp",
                tags = listOf("Wasm Web", "Android", "iOS", "Desktop", "Material 3"),
                deliveryHint = "One codebase, all screens",
            ),
            FreelanceService(
                title = "Cross-Platform Mobile Development",
                description = "React Native, Flutter, SwiftUI, and UIKit solutions for multi-platform reach.",
                icon = "cross",
                tags = listOf("React Native", "Flutter", "SwiftUI", "UIKit"),
                deliveryHint = "Platform-native feel",
            ),
            FreelanceService(
                title = "UI/UX Implementation",
                description = "Pixel-perfect, animated interfaces with modern design patterns and responsive layouts.",
                icon = "ui",
                tags = listOf("Figma to Code", "Animations", "Design Systems", "Accessibility"),
                deliveryHint = "Design handoff → production UI",
            ),
            FreelanceService(
                title = "API Integration",
                description = "RESTful APIs, WebSockets, Firebase, and third-party SDK integrations.",
                icon = "api",
                tags = listOf("Retrofit", "Ktor", "Socket.IO", "Firebase", "Supabase"),
                deliveryHint = "Real-time & offline-first",
            ),
            FreelanceService(
                title = "Performance Optimization",
                description = "Multithreading, caching, compression strategies, and app profiling.",
                icon = "perf",
                tags = listOf("Profiling", "Caching", "Compression", "Startup Time"),
                deliveryHint = "Measurable speed gains",
            ),
            FreelanceService(
                title = "AI Integration & On-Device ML",
                description = "Trending AI features — Gemini/OpenAI APIs, ML Kit, TensorFlow Lite, chatbots, and smart automation in mobile apps.",
                icon = "ai",
                tags = listOf("Gemini API", "OpenAI", "ML Kit", "TFLite", "RAG", "On-device AI"),
                deliveryHint = "From AI chat to on-device inference",
            ),
            FreelanceService(
                title = "App Maintenance & Support",
                description = "Bug fixes, feature updates, testing, and long-term app maintenance.",
                icon = "support",
                tags = listOf("Bug Fixes", "SDK Updates", "CI/CD", "Monitoring"),
                deliveryHint = "Monthly retainer available",
            ),
        ),
        testimonials = listOf(
            Testimonial(
                clientName = "Krishna Nand Yadav",
                role = "Data Analyst",
                company = "Analytics & Insights",
                content = "Shashank partnered with our data team to ship mobile KPI dashboards and reliable API layers. " +
                    "He translated analytics requirements into clean Kotlin architecture, met every sprint deadline, " +
                    "and communicated blockers early. A dependable engineer who delivers production-ready work.",
                rating = 5,
            ),
            Testimonial(
                clientName = "RRBMU College",
                role = "Academic Partner",
                company = "Rajasthan, India",
                content = "Shashank built RRBMU Studies for our university — 1000+ downloads and strong Play Store ratings. " +
                    "He handled Firebase, push notifications, AdMob, and content workflows with professionalism. " +
                    "Students love the app; we recommend him for any academic or campus-tech project.",
                rating = 5,
            ),
            Testimonial(
                clientName = "Bristol University",
                role = "Research Collaborator",
                company = "Bristol, United Kingdom",
                content = "During our Federated Learning collaboration, Shashank delivered an offline-first health-tracking " +
                    "Android prototype with Room sync, Google Fit integration, and Jetpack Compose UI. " +
                    "His engineering discipline and research mindset made remote cross-border teamwork seamless.",
                rating = 5,
            ),
        ),
        socialLinks = listOf(
            SocialLink("Resume", "https://drive.google.com/file/d/1GKQkZOtrKGyvd9rDtuEnEmYtJ6i5klmS/view?usp=sharing", "resume"),
            SocialLink("LinkedIn", "https://www.linkedin.com/in/shashank142004/", "linkedin"),
            SocialLink("GitHub", "https://github.com/Shashank9759/", "github"),
            SocialLink("Topmate", "https://topmate.io/shashank_ranjan10", "topmate"),
            SocialLink("Email", "mailto:shashankranjantech@gmail.com", "email"),
        ),
    )
}
