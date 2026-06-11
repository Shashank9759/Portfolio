# Shashank Ranjan — Portfolio Website

A modern, professional portfolio website built with **Compose Multiplatform (CMP)** targeting the web via **Kotlin/Wasm**. Showcases mobile development expertise, projects, experience, and freelancing services.

## Features

- **Responsive design** — Optimized for desktop, tablet, and mobile
- **Dark & Light theme** — Toggle via navigation bar
- **Scroll-triggered animations** — Fade-in reveals, animated counters, and skill bars
- **Glassmorphism UI** — Premium design with gradients and glass effects
- **Clean Architecture** — Separated domain, data, and presentation layers
- **SEO-friendly** — Semantic HTML meta tags in `index.html`

## Tech Stack

| Layer | Technology |
|-------|-----------|
| UI Framework | Compose Multiplatform 1.8 |
| Language | Kotlin 2.1 |
| Web Target | Kotlin/Wasm (wasmJs) |
| Architecture | Clean Architecture (Domain → Data → Presentation) |
| Design | Material 3, Custom Theme |

## Project Structure

```
Portfolio/
├── composeApp/
│   └── src/
│       ├── commonMain/kotlin/com/shashank/portfolio/
│       │   ├── data/              # Data sources & repositories
│       │   ├── domain/model/      # Domain models
│       │   └── presentation/
│       │       ├── animation/     # Scroll & counter animations
│       │       ├── components/    # Reusable UI components
│       │       ├── navigation/    # Section navigation
│       │       ├── screens/       # Portfolio sections
│       │       ├── theme/         # Colors, typography, theming
│       │       └── viewmodel/     # State management
│       └── wasmJsMain/            # Web entry point & platform APIs
├── skills.md                      # Technical skills documentation
└── README.md
```

## Prerequisites

- **JDK 17** (required — JDK 21+ may cause Gradle/Kotlin compatibility issues)
- **Gradle 8.12+** (wrapper included)

> Set `JAVA_HOME` to JDK 17 if you have multiple JDK versions installed:
> ```bash
> export JAVA_HOME=$(/usr/libexec/java_home -v 17)
> ```

## Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd Portfolio
   ```

2. Run the development server:
   ```bash
   ./gradlew :composeApp:wasmJsBrowserDevelopmentRun
   ```

3. Open the URL printed in the terminal (typically `http://localhost:8080`).

## Build for Production

```bash
./gradlew :composeApp:wasmJsBrowserProductionWebpack
```

Production output is generated at:
```
composeApp/build/dist/wasmJs/productionExecutable/
```

Deploy the contents of this directory to any static hosting service.

## Deployment Options

### GitHub Pages

1. Build the production bundle (command above).
2. Copy `composeApp/build/dist/wasmJs/productionExecutable/` contents to your `gh-pages` branch or `docs/` folder.
3. Enable GitHub Pages in repository settings.

### Netlify / Vercel

1. Build command: `./gradlew :composeApp:wasmJsBrowserProductionWebpack`
2. Publish directory: `composeApp/build/dist/wasmJs/productionExecutable`

### Custom Server

Serve the production directory with any static file server (Nginx, Apache, Caddy).

> **Note:** Kotlin/Wasm requires browsers with WebAssembly GC support (Chrome 119+, Firefox 120+, Safari 18+).

## Customization

### Update Portfolio Content

Edit `composeApp/src/commonMain/kotlin/com/shashank/portfolio/data/source/PortfolioDataSource.kt` to update:

- Personal information and contact details
- Work experience and education
- Projects and skills
- Freelancing services

### Update Theme

Modify files in `presentation/theme/`:

- `Color.kt` — Color palette
- `Typography.kt` — Font styles
- `Theme.kt` — Light/dark color schemes

### Resume Download Link

Update `resumeUrl` in `PortfolioDataSource.kt` with your Google Drive or hosted resume PDF link.

## Contact

- **Email:** shashankranjantech@gmail.com
- **LinkedIn:** [shashank142004](https://www.linkedin.com/in/shashank142004/)
- **GitHub:** [Shashank9759](https://github.com/Shashank9759/)

## License

This project is for personal portfolio use. All rights reserved © Shashank Ranjan.
