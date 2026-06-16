# Web Portfolio (HTML/CSS)

Semantic **HTML + CSS** version of the portfolio — same content and Midnight theme as the Compose Multiplatform Wasm app, but built for the web:

- Selectable/copyable text
- Browser DevTools inspector
- Fast static load (no Wasm bundle)
- SEO-friendly markup

Content is generated from the shared `PortfolioDataSource` using **Kotlin `kotlinx-html`**.

## Run

### Option A — instant (recommended)

```bash
./gradlew :webPortfolio:openWeb
```

Builds the site and opens `index.html` in your browser. **No server, Gradle finishes immediately.**

### Option B — local server

```bash
./gradlew :webPortfolio:run
```

Gradle stays at **~92% EXECUTING** — that means the server is running (not stuck). Your browser should open automatically to **http://localhost:3000**. Press **Enter** in the terminal to stop.

### Option C — manual

```bash
./gradlew :webPortfolio:generateWebDist
open webPortfolio/build/dist/index.html
```

## Generate static files only

```bash
./gradlew :webPortfolio:generateWebDist
```

Output: `webPortfolio/build/dist/` (`index.html`, `styles.css`, `app.js`, `assets/`)

Deploy that folder to GitHub Pages, Netlify, or Vercel.

## Branch

This module lives on branch **`webTargetPorfolio`**. The `main` branch keeps the original CMP Wasm app unchanged.

## Structure

| File | Purpose |
|------|---------|
| `SiteGenerator.kt` | Builds `index.html` from portfolio data |
| `ImageAssets.kt` | Maps image keys → bundled PNGs |
| `resources/web/styles.css` | Midnight theme CSS |
| `resources/web/app.js` | Nav, services tabs, contact mailto |
| `Main.kt` | Serves `build/dist` via Ktor (dev/preview) |

Assets are copied from `composeApp/src/commonMain/composeResources/drawable/`.
