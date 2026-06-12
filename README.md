# Shashank Ranjan — Portfolio

A professional portfolio built with **Compose Multiplatform (CMP)** — one Kotlin codebase, six platforms. Showcases experience, projects, skills, services, testimonials, and contact options with a polished, responsive UI.

**Live focus:** Web deployment + native apps for Android, iOS, macOS, and desktop (Windows/Linux/macOS via JVM).

---

## Features

- **Multiplatform** — Web (Wasm), Android, iOS, native macOS, and JVM desktop from shared UI
- **14 theme modes** — Midnight, Light, Android, Ocean, Cyber, AMOLED, and more
- **Responsive layout** — Mobile, tablet, and desktop breakpoints with touch-aware behavior
- **Interactive background** — Physics-based device mockups on desktop (disabled on touch for performance)
- **Sticky navigation** — Section scroll, theme picker overlay, recruiter quick bar, floating contact CTA
- **Clean Architecture** — Domain → Data → Presentation
- **SEO-friendly web** — Semantic meta tags in `index.html`

---

## Supported Platforms

| Platform | Technology | How to run |
|----------|------------|------------|
| **Web** | Kotlin/Wasm | `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` |
| **Android** | Jetpack Compose | `./gradlew :composeApp:installDebug` |
| **iOS** | Compose Multiplatform + Xcode | Open `iosApp/iosApp.xcodeproj` |
| **macOS** | Native Kotlin/Native executable | `./gradlew :composeApp:runDebugExecutableMacosArm64` |
| **Desktop** | Compose Desktop (JVM) | `./gradlew :composeApp:run` |

> **Windows & Linux** use the **desktop (JVM)** target. Build a Windows installer with `packageMsi` on a Windows machine.

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| UI | Compose Multiplatform 1.8.2, Material 3 |
| Language | Kotlin 2.1.21 |
| Targets | `wasmJs`, `androidTarget`, `ios*`, `macosArm64`, `macosX64`, `jvm("desktop")` |
| Architecture | Clean Architecture (Domain → Data → Presentation) |
| Build | Gradle 8.12+ (wrapper included), AGP 8.5.2 |

---

## Project Structure

```
Portfolio/
├── composeApp/
│   ├── build.gradle.kts
│   └── src/
│       ├── commonMain/          # Shared UI, data, domain, theme
│       ├── androidMain/         # MainActivity, Android platform APIs
│       ├── wasmJsMain/          # Web entry, index.html, browser APIs
│       ├── iosMain/             # MainViewController, iOS platform APIs
│       ├── macosMain/           # Native macOS app entry
│       └── desktopMain/         # JVM desktop (Windows, Linux, macOS)
├── iosApp/                      # Xcode project for iOS simulator/device
├── gradle/libs.versions.toml
├── skills.md                    # AI assistant project guide (Cursor, Claude, etc.)
└── README.md
```

### Key source files

| Purpose | Path |
|---------|------|
| Portfolio content (edit here) | `composeApp/src/commonMain/.../data/source/PortfolioDataSource.kt` |
| Root UI | `composeApp/src/commonMain/.../presentation/App.kt` |
| Theme & responsive | `composeApp/src/commonMain/.../presentation/theme/` |
| Web entry | `composeApp/src/wasmJsMain/kotlin/.../main.kt` |
| Web HTML | `composeApp/src/wasmJsMain/resources/index.html` |

For a full file map and AI editing conventions, see **[skills.md](skills.md)**.

---

## Prerequisites

- **JDK 17** (required — JDK 21+ may cause compatibility issues)
- **Gradle** — wrapper included (`./gradlew`)
- **Android** — Android Studio + SDK 35 for mobile builds
- **iOS** — Xcode 15+, Apple Developer account for device builds
- **macOS native** — Xcode Command Line Tools

### Set JDK 17

**macOS:**
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

**Windows (PowerShell):**
```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
```

**Linux:**
```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
```

---

## Quick Start

### 1. Clone

```bash
git clone https://github.com/Shashank9759/Portfolio.git
cd Portfolio
```

### 2. Run on your platform

**Web (any OS):**
```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```
Open `http://localhost:8080` (URL printed in terminal).

**Android:**
```bash
./gradlew :composeApp:installDebug
```

**Desktop (Windows / Linux / macOS):**
```bash
./gradlew :composeApp:run
```

**macOS (native app):**
```bash
./gradlew :composeApp:runDebugExecutableMacosArm64   # Apple Silicon
./gradlew :composeApp:runDebugExecutableMacosX64     # Intel Mac
```

**iOS:**
1. Set your Team ID in `iosApp/Configuration/Config.xcconfig`
2. Open `iosApp/iosApp.xcodeproj` in Xcode
3. Select a simulator or device and Run

---

## Build for Production

### Web

```bash
./gradlew :composeApp:wasmJsBrowserProductionWebpack
```

Output: `composeApp/build/dist/wasmJs/productionExecutable/`

Deploy that folder to any static host (GitHub Pages, Netlify, Vercel, Nginx, etc.).

> Kotlin/Wasm needs browsers with WebAssembly GC support: Chrome 119+, Firefox 120+, Safari 18+.

### Desktop installers

```bash
./gradlew :composeApp:packageDmg    # macOS (.dmg)
./gradlew :composeApp:packageMsi    # Windows (.msi) — build on Windows
./gradlew :composeApp:packageDeb    # Linux (.deb)
```

---

## Deployment (Web)

### GitHub Pages

1. `./gradlew :composeApp:wasmJsBrowserProductionWebpack`
2. Copy `composeApp/build/dist/wasmJs/productionExecutable/` to `gh-pages` branch or `docs/`
3. Enable Pages in repository settings

### Netlify / Vercel

| Setting | Value |
|---------|-------|
| Build command | `./gradlew :composeApp:wasmJsBrowserProductionWebpack` |
| Publish directory | `composeApp/build/dist/wasmJs/productionExecutable` |

---

## Customization

### Update portfolio content

Edit `composeApp/src/commonMain/kotlin/com/shashank/portfolio/data/source/PortfolioDataSource.kt`:

- Personal info, contact links, resume URL
- Work experience, education, projects
- Skills, services, testimonials

### Update theme

Files in `presentation/theme/`:

- `Color.kt` — palette
- `PortfolioThemeMode.kt` — 14 theme modes
- `Typography.kt` — fonts
- `Responsive.kt` — breakpoints and mobile/desktop tokens

### Platform-specific behavior

- `Platform.kt` + `Platform.*.kt` — open URL, download, email
- `PlatformUi.kt` + `PlatformUi.*.kt` — touch vs pointer (hover, physics)

---

## Compile All Targets (CI)

```bash
./gradlew :composeApp:compileKotlinWasmJs \
         :composeApp:compileDebugKotlinAndroid \
         :composeApp:compileKotlinDesktop \
         :composeApp:compileKotlinIosSimulatorArm64 \
         :composeApp:compileKotlinMacosArm64
```

---

## Contact

- **Email:** [shashankranjantech@gmail.com](mailto:shashankranjantech@gmail.com)
- **LinkedIn:** [shashank142004](https://www.linkedin.com/in/shashank142004/)
- **GitHub:** [Shashank9759](https://github.com/Shashank9759/)

---

## License

Personal portfolio project. All rights reserved © Shashank Ranjan.
