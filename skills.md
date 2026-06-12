# Portfolio — AI Project Guide

> **Purpose:** This file helps AI assistants (Cursor, Claude, Copilot, etc.) understand this repository quickly and make correct changes without breaking multiplatform builds.
>
> **Owner:** Shashank Ranjan — SDE-1, Android & Cross-Platform Mobile Engineer  
> **Location:** Noida, Uttar Pradesh, India  
> **Contact:** shashankranjantech@gmail.com

---

## What This Project Is

A **Compose Multiplatform (CMP)** personal portfolio app for **Shashank Ranjan**. One shared Kotlin UI (`commonMain`) runs on:

| Platform | Source set | Entry point | Run command |
|----------|------------|-------------|-------------|
| **Web** | `wasmJsMain` | `main.kt` → `ComposeViewport("ComposeTarget")` | `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` |
| **Android** | `androidMain` | `MainActivity.kt` | `./gradlew :composeApp:installDebug` |
| **iOS** | `iosMain` | `MainViewController.kt` | Open `iosApp/iosApp.xcodeproj` in Xcode |
| **macOS (native)** | `macosMain` | `main.kt` → `NSApplication` + `Window` | `./gradlew :composeApp:runDebugExecutableMacosArm64` |
| **Desktop (JVM)** | `desktopMain` | `Main.kt` → `application { Window }` | `./gradlew :composeApp:run` |

**Windows / Linux** use the **desktop (JVM)** target — not macOS native.  
**Web** is deployed as static Wasm output to GitHub Pages, Netlify, Vercel, etc.

---

## Tech Stack (Pinned Versions)

| Item | Version |
|------|---------|
| Kotlin | 2.1.21 |
| Compose Multiplatform | 1.8.2 |
| Android Gradle Plugin | 8.5.2 |
| compileSdk / targetSdk | 35 |
| minSdk | 24 |
| JDK | **17 required** (21+ can break Gradle/Kotlin) |

Gradle properties: `org.jetbrains.compose.experimental.macos.enabled=true` (native macOS).

---

## Architecture

```
domain/          → PortfolioModels.kt (data classes)
data/            → PortfolioDataSource.kt (static content), PortfolioRepository.kt
presentation/    → UI, theme, navigation, animations, viewmodel
Platform.kt      → expect openUrl, downloadFile, openEmail
PlatformUi.kt    → expect isTouchPlatform()
```

**Pattern:** Clean Architecture — Domain → Data → Presentation.  
**State:** `PortfolioViewModel` loads data from `PortfolioRepository` → `PortfolioDataSource`.  
**Root composable:** `presentation/App.kt` — wraps content in `ProvideResponsiveLayout` + `PortfolioTheme`.

### UI Sections (in scroll order)

1. Hero — `HeroSection.kt`
2. About — `AboutSection.kt`
3. Skills — `SkillsSection.kt`
4. Experience — `ExperienceSection.kt`
5. Projects — `ProjectsSection.kt`
6. Services — `ServicesSection.kt`
7. Testimonials — `TestimonialsSection.kt`
8. Contact — `ContactSection.kt`
9. Footer — `FooterSection.kt`

**Navigation:** `NavigationBar.kt` (sticky), `SectionScrollRegistry.kt` (scroll-to-section), `FloatingContactFab.kt`, `RecruiterQuickBar.kt`.

---

## Directory Map

```
Portfolio/
├── composeApp/
│   ├── build.gradle.kts          # All KMP targets + compose.desktop packaging
│   └── src/
│       ├── commonMain/kotlin/com/shashank/portfolio/
│       │   ├── data/source/PortfolioDataSource.kt   ← EDIT CONTENT HERE
│       │   ├── domain/model/PortfolioModels.kt
│       │   ├── presentation/
│       │   │   ├── App.kt                           ← Root UI
│       │   │   ├── screens/                         ← Section composables
│       │   │   ├── components/                      ← Nav, FAB, canvas background
│       │   │   ├── theme/                           ← Colors, Responsive, ThemeMode
│       │   │   ├── animation/                       ← Physics, scroll animations
│       │   │   ├── navigation/
│       │   │   └── viewmodel/PortfolioViewModel.kt
│       │   ├── Platform.kt                        ← expect declarations
│       │   └── PlatformUi.kt
│       ├── androidMain/                           ← MainActivity, Platform.android.kt
│       ├── wasmJsMain/                            ← main.kt, index.html, Platform.wasmJs.kt
│       ├── iosMain/                               ← MainViewController.kt, Platform.ios.kt
│       ├── macosMain/                             ← Native macOS entry + Platform.macos.kt
│       └── desktopMain/                           ← JVM desktop (Win/Mac/Linux)
├── iosApp/                                        ← Xcode wrapper for iOS
│   ├── Configuration/Config.xcconfig              ← Set TEAM_ID, BUNDLE_ID
│   └── iosApp.xcodeproj
├── gradle/libs.versions.toml
├── gradle.properties
├── skills.md                                      ← This file (AI + project context)
└── README.md                                      ← Human-facing documentation
```

---

## Common AI Tasks — Where to Edit

| Task | File(s) |
|------|---------|
| Change name, email, links, resume URL | `PortfolioDataSource.kt` |
| Add/edit job, project, skill, testimonial | `PortfolioDataSource.kt` + `PortfolioModels.kt` if new fields |
| Change colors / theme modes | `theme/Color.kt`, `theme/PortfolioThemeMode.kt`, `theme/Theme.kt` |
| Typography / spacing | `theme/Typography.kt`, `theme/Dimensions.kt`, `theme/Responsive.kt` |
| Nav links / section IDs | `navigation/Navigation.kt`, `NavigationBar.kt` |
| Web HTML shell / loader | `wasmJsMain/resources/index.html` |
| Web entry (blank screen bugs) | `wasmJsMain/kotlin/.../main.kt` — must use `ComposeViewport("ComposeTarget")` |
| Android edge-to-edge / insets | `MainActivity.kt`, `NavigationBar.kt` (`statusBarsPadding`) |
| Open URL / email per platform | `Platform.*.kt` in each `*Main` source set |
| Touch vs desktop behavior | `PlatformUi.*.kt` — `isTouchPlatform()` drives `Responsive.kt` |
| iOS Xcode signing | `iosApp/Configuration/Config.xcconfig` |

---

## Coding Conventions (Follow These)

1. **Shared UI in `commonMain` only** — platform code stays in `*Main` source sets via `expect`/`actual`.
2. **Minimize scope** — match existing naming, imports, and Material 3 patterns.
3. **Responsive system** — use `LocalResponsiveConfig` / `ProvideResponsiveLayout`; mobile disables physics/hover.
4. **Wasm-safe UI** — no emoji in theme picker labels (use text + colored dots); broken glyphs on web.
5. **Web container** — `index.html` must have `<div id="ComposeTarget">`, not a raw `<canvas>`.
6. **Do not use Android NDK APIs on Wasm** — canvas/physics uses Skia via `InteractiveDevBackground.kt`.
7. **14 theme modes** — `PortfolioThemeMode` enum; picker is overlay `DropdownMenu` in `ThemeModePicker.kt`.
8. **Contact email** — Wasm opens Gmail compose URL; Android uses `Intent`; desktop/iOS/macOS use `mailto:` or system handlers.

---

## Platform-Specific Gotchas

### Web (Wasm)
- Entry: `ComposeViewport(viewportContainerId = "ComposeTarget")`
- `openEmail` uses Gmail web compose (reliable in browser)
- Requires browsers with Wasm GC (Chrome 119+, Firefox 120+, Safari 18+)

### Android
- `enableEdgeToEdge()` in `MainActivity`
- `AndroidContextProvider` required for `Platform.android.kt`
- `isTouchPlatform() = true` → minimal background intensity, no hover animations

### iOS
- `MainViewController()` exported to Swift as `MainViewControllerKt.MainViewController()`
- Framework name: `ComposeApp` (static)
- Build via Xcode; Gradle task `embedAndSignAppleFrameworkForXcode` needs Xcode env vars
- Set `TEAM_ID` in `iosApp/Configuration/Config.xcconfig`

### macOS (native)
- Uses `NSApplication.sharedApplication()` + `Window(title, size) { App() }` + `NSApp?.run()`
- **Not** the JVM `application { }` API — different Window signature
- Experimental: `org.jetbrains.compose.experimental.macos.enabled=true`

### Desktop (JVM — Windows, Linux, macOS)
- `compose.desktop` with `mainClass = com.shashank.portfolio.MainKt`
- Package: `packageMsi` (Windows), `packageDmg` (macOS), `packageDeb` (Linux)
- Build Windows `.msi` **on Windows** (or Windows CI)

---

## Build Commands (macOS/Linux shell)

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)   # macOS; use JDK 17 path on Win/Linux

# Web
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
./gradlew :composeApp:wasmJsBrowserProductionWebpack

# Android
./gradlew :composeApp:installDebug

# Desktop (JVM — all desktop OS)
./gradlew :composeApp:run
./gradlew :composeApp:packageDmg    # macOS installer
./gradlew :composeApp:packageMsi    # Windows installer (run on Windows)
./gradlew :composeApp:packageDeb    # Linux

# macOS native
./gradlew :composeApp:runDebugExecutableMacosArm64   # Apple Silicon
./gradlew :composeApp:runDebugExecutableMacosX64       # Intel Mac

# Compile checks (CI-friendly)
./gradlew :composeApp:compileKotlinWasmJs
./gradlew :composeApp:compileDebugKotlinAndroid
./gradlew :composeApp:compileKotlinDesktop
./gradlew :composeApp:compileKotlinIosSimulatorArm64
./gradlew :composeApp:compileKotlinMacosArm64
```

---

## Key Features (Do Not Break Accidentally)

- **14 theme modes** with dropdown overlay (no layout jump)
- **Physics/canvas background** (`InteractiveDevBackground`, `PhysicsDeviceEngine`) — desktop only
- **Sticky nav** with `statusBarsPadding` on mobile
- **Scroll-based section navigation** via `SectionScrollRegistry`
- **Recruiter quick bar** + floating “Let’s Talk” FAB
- **Responsive breakpoints** in `Responsive.kt` (mobile &lt;600dp, tablet, desktop)

---

## About Shashank (Context for Content Edits)

**Role:** SDE-1 — Android & Cross-Platform Mobile Engineer at Lifease Solutions LLP  
**Focus:** Android Mobile, Android TV (Leanback), Kotlin, Jetpack Compose, KMP/CMP, iOS (SwiftUI/UIKit), cross-platform (RN, Flutter)

**Notable work:**
- CricRadio — 100K+ downloads, real-time cricket (Kotlin, Compose, Ktor, Socket.IO)
- RRBMU Studies — university app, 4.6★ rating
- SkinLens — ML/TFLite skin disease detection
- Bristol University — Federated Learning research collaboration
- 95% data compression optimization at Lifease Solutions

**Core skills:** Kotlin, Java, Jetpack Compose, CMP, MVVM/MVI/Clean Architecture, Room, Ktor, Firebase, TFLite, testing (JUnit, Espresso, MockK)

When editing portfolio copy, keep tone **professional, concise, recruiter-friendly**. Data lives in `PortfolioDataSource.kt` — keep it consistent with resume facts.

---

## AI Assistant Checklist Before Submitting Changes

- [ ] UI changes in `commonMain` unless platform-specific
- [ ] New platform API → add `expect` in common + `actual` in every target source set
- [ ] Wasm: no emoji labels; test `ComposeTarget` container if touching web entry
- [ ] Touch platforms: verify responsive config still disables heavy animations on mobile
- [ ] Content changes only in `PortfolioDataSource.kt` when possible
- [ ] Run relevant compile task for touched targets
- [ ] JDK 17 — do not upgrade Kotlin/Compose versions without user request

---

*Last updated: June 2026*
