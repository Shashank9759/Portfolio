# Portfolio — AI Project Guide (Web Branch)

> **Branch:** `webTargetPorfolio` — **web-only**. Kobweb + Compose HTML. No Android/iOS/Wasm targets.

## What This Is

Portfolio site built with **Kobweb** and **Compose HTML**, data from `shared/PortfolioDataSource.kt`. Same CSS/JS assets as before (themes, animations, canvas, music player).

| Module | Purpose |
|--------|---------|
| `shared` | Kotlin/JS data + models |
| `site` | Kobweb app (`@Page` composables + public assets) |

## Run

```bash
./gradlew :site:kobwebStart    # http://localhost:3000
./gradlew :site:kobwebStop
```

## Export (GitHub Pages)

```bash
./gradlew :site:kobwebExport -PkobwebExportLayout=STATIC
```

Output: `site/.kobweb/site/`

## Web assets

| File | Purpose |
|------|---------|
| `site/src/site/components/sections/PortfolioSections.kt` | Page sections |
| `site/src/site/components/PortfolioShell.kt` | Canvas, dancer, music widget |
| `themes.css` | 17 theme modes |
| `animations.css` | Scroll reveal, FAB pulse, skill shimmer |
| `styles.css` | Layout, glass cards, grids |
| `background.js` | Solar system + canvas particles |
| `music-player.js` | Sahiba player + bhangra dancer |
| `app.js` | Theme picker, nav, counters, services auto-cycle |

## Edit content

`shared/src/commonMain/.../PortfolioDataSource.kt`

## Edit visuals

- CSS/JS: `site/src/jsMain/resources/public/`
- HTML structure: `site/src/site/components/`
