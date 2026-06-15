# Shashank Ranjan — Portfolio

A professional portfolio built with **Compose Multiplatform (CMP)** — one Kotlin codebase, six platforms. Showcases experience, client work, projects, skills, freelancing services, testimonials, and contact options with a polished, responsive UI.

**Live focus:** Web deployment + native apps for Android, iOS, macOS, and desktop (Windows/Linux/macOS via JVM).

---

## Features

- **Multiplatform** — Web (Wasm), Android, iOS, native macOS, and JVM desktop from shared UI
- **14 theme modes** — Midnight, Light, Android, Ocean, Cyber, AMOLED, and more
- **Responsive layout** — Mobile, tablet, and desktop breakpoints with touch-aware behavior
- **Interactive background** — Physics-based device mockups on desktop (disabled on touch for performance)
- **Sticky navigation** — Section scroll, theme picker overlay, recruiter quick bar, floating contact CTA
- **Client showcase** — “Helping Organisations Build Apps” section with app posters and Play Store links
- **Bundled brand assets** — Hero tech logos, social link icons, and project/org posters (no broken CDN links on critical UI)
- **Optional live API** — Ktor server on port 8090; app falls back to bundled data if unavailable
- **Clean Architecture** — Domain → Data → Presentation across `shared` + `composeApp` modules
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
| Shared data | `shared` module — models + `PortfolioDataSource` |
| Optional API | Ktor server (`server` module), port **8090** |
| Architecture | Clean Architecture (Domain → Data → Presentation) |
| Build | Gradle 8.12+ (wrapper included), AGP 8.5.2 |

---

## Project Structure

```
Portfolio/
├── composeApp/                  # UI, theme, navigation, platform entry points
│   ├── build.gradle.kts
│   └── src/
│       ├── commonMain/
│       │   ├── kotlin/.../presentation/   # App.kt, screens, components, theme
│       │   ├── kotlin/.../data/remote/    # PortfolioApiClient (expect/actual)
│       │   └── composeResources/drawable/ # Project posters, brand logos, org assets
│       ├── androidMain/
│       ├── wasmJsMain/          # Web entry, index.html, fetch()-based API client
│       ├── iosMain/
│       ├── macosMain/
│       └── desktopMain/
├── shared/                      # Domain models + static portfolio content
│   └── src/commonMain/kotlin/com/shashank/portfolio/
│       ├── domain/model/PortfolioModels.kt
│       └── data/source/PortfolioDataSource.kt   ← EDIT CONTENT HERE
├── server/                      # Optional Ktor API (GET /api/portfolio)
├── iosApp/                      # Xcode project for iOS simulator/device
├── gradle/libs.versions.toml
├── skills.md                    # AI assistant project guide (Cursor, Claude, etc.)
└── README.md
```

### Key source files

| Purpose | Path |
|---------|------|
| Portfolio content (edit here) | `shared/src/commonMain/kotlin/com/shashank/portfolio/data/source/PortfolioDataSource.kt` |
| Data models | `shared/src/commonMain/kotlin/com/shashank/portfolio/domain/model/PortfolioModels.kt` |
| Root UI | `composeApp/src/commonMain/kotlin/com/shashank/portfolio/presentation/App.kt` |
| Theme & responsive | `composeApp/src/commonMain/kotlin/com/shashank/portfolio/presentation/theme/` |
| Bundled images | `composeApp/src/commonMain/composeResources/drawable/` |
| Web entry | `composeApp/src/wasmJsMain/kotlin/.../main.kt` |
| Web HTML | `composeApp/src/wasmJsMain/resources/index.html` |
| Optional API server | `server/src/main/kotlin/com/shashank/portfolio/server/Application.kt` |

For a full file map and AI editing conventions, see **[skills.md](skills.md)**.

---

## UI Sections (scroll order)

1. **Hero** — intro, CTA, stats (downloads, projects, Play Store rating)
2. **About** — summary, career highlights, education
3. **Skills** — categorized skill bars with brand visuals
4. **Work Experience** — job cards (Flexo Technology has no external link)
5. **Helping Organisations Build Apps** — client posters (Lifease, Bristol, RRBMU, Putatoe)
6. **Featured Projects** — project grid with bundled posters
7. **Freelancing Services** — selectable service cards + stats bar
8. **Testimonials** — client reviews
9. **Contact** — form (mailto), social link cards
10. **Footer**

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

### 3. Optional — live API server

The app works fully offline with bundled data. To serve live JSON (e.g. for the Services “Live data” badge):

```bash
./gradlew :server:run
```

API runs on **http://localhost:8090** (`GET /api/portfolio`). Web dev server uses port **8080**, so API uses **8090** to avoid conflicts.

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

Edit `shared/src/commonMain/kotlin/com/shashank/portfolio/data/source/PortfolioDataSource.kt`:

- Personal info, contact links, resume URL
- Work experience, education, projects
- Client organisations (`clientOrganizations`) — posters via `imageKey` in `composeResources/drawable/`
- Skills, services, testimonials

Add new image assets under `composeApp/src/commonMain/composeResources/drawable/` and register the key in `ProjectImage.kt`.

### Update theme

Files in `composeApp/.../presentation/theme/`:

- `Color.kt` — palette
- `PortfolioThemeMode.kt` — 14 theme modes
- `Typography.kt` — fonts
- `Responsive.kt` — breakpoints and mobile/desktop tokens

### Platform-specific behavior

- `Platform.kt` + `Platform.*.kt` — open URL, download, email
- `PlatformUi.kt` + `PlatformUi.*.kt` — touch vs pointer (hover, physics)
- `PortfolioApiClient.*.kt` — HTTP fetch per platform (Wasm uses `fetch()`, others use Ktor client)

---

## Compile All Targets (CI)

```bash
./gradlew :composeApp:compileKotlinWasmJs \
         :composeApp:compileDebugKotlinAndroid \
         :composeApp:compileKotlinDesktop \
         :composeApp:compileKotlinIosSimulatorArm64 \
         :composeApp:compileKotlinMacosArm64 \
         :shared:compileKotlinJvm
```

---

## Contact

- **Email:** [shashankranjantech@gmail.com](mailto:shashankranjantech@gmail.com)
- **LinkedIn:** [shashank142004](https://www.linkedin.com/in/shashank142004/)
- **GitHub:** [Shashank9759](https://github.com/Shashank9759/)
- **Topmate:** [shashank_ranjan10](https://topmate.io/shashank_ranjan10)

---

## License

Personal portfolio project. All rights reserved © Shashank Ranjan.
