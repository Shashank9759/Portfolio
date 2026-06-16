# Shashank Ranjan — Portfolio (Web)

**Branch `webTargetPorfolio`** — HTML/CSS web portfolio only. No Wasm, Android, iOS, or server modules.

Kotlin **`kotlinx-html`** generates semantic HTML from the shared `PortfolioDataSource`. Styling and animations match the original Compose Multiplatform portfolio (17 themes, scroll reveals, canvas background, services grid, skill headers, etc.).

## Run

```bash
./gradlew :webPortfolio:generateWebDist   # build static site
./gradlew :webPortfolio:run               # preview at http://localhost:3000
```

Open `webPortfolio/build/dist/index.html` directly for instant preview (no server).

## Structure

```
shared/           → portfolio data (JVM)
webPortfolio/
  SiteGenerator.kt    → kotlinx-html page builder
  resources/web/
    themes.css        → 17 theme modes
    animations.css    → scroll reveal, FAB pulse, etc.
    styles.css        → layout & components
    background.js     → canvas dev background
    app.js            → theme picker, nav, counters, services
  resources/assets/   → PNG posters & brand logos
```

## Deploy

Upload `webPortfolio/build/dist/` to GitHub Pages, Netlify, or Vercel.
