# Portfolio — AI Project Guide (Web Branch)

> **Branch:** `webTargetPorfolio` — **web-only**. No Compose Multiplatform Wasm/Android/iOS on this branch.

## What This Is

HTML/CSS portfolio generated with **Kotlin `kotlinx-html`** from `shared/PortfolioDataSource.kt`.

| Module | Purpose |
|--------|---------|
| `shared` | JVM-only data + models |
| `webPortfolio` | HTML generator + CSS/JS assets |

## Run

```bash
./gradlew :webPortfolio:generateWebDist
./gradlew :webPortfolio:run    # http://localhost:3000
```

## Web assets

| File | Purpose |
|------|---------|
| `SiteGenerator.kt` | Builds `index.html` |
| `themes.css` | 17 theme modes (matches `PortfolioThemeMode`) |
| `animations.css` | Scroll reveal, FAB pulse, skill shimmer |
| `styles.css` | Layout, glass cards, grids |
| `background.js` | Canvas particles + aurora (desktop) |
| `app.js` | Theme picker, nav, counters, services auto-cycle |

## Edit content

`shared/src/commonMain/.../PortfolioDataSource.kt`

## Edit visuals

- CSS: `webPortfolio/src/main/resources/web/*.css`
- HTML structure: `SiteGenerator.kt`
- Interactions: `app.js`, `background.js`

*Last updated: June 2026*
