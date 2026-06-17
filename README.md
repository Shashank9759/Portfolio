# Shashank Ranjan — Portfolio (Web)

**Branch `webTargetPorfolio`** — Kotlin **Kobweb** + Compose HTML portfolio. Same UI, themes, animations, music player, and canvas background as the previous static generator.

## Run

```bash
./gradlew :site:kobwebStart          # dev server at http://localhost:3000
./gradlew :site:kobwebStop           # stop dev server
```

## Build for GitHub Pages

```bash
./gradlew :site:kobwebExport -PkobwebExportLayout=STATIC
```

Static output: `site/.kobweb/site/` (upload or use the GitHub Actions workflow).

## Structure

```
shared/              → portfolio data (Kotlin/JS)
site/
  src/site/          → Kobweb @Page composables
  src/jsMain/resources/public/
    themes.css       → 17 theme modes
    animations.css   → scroll reveal, FAB pulse, etc.
    styles.css       → layout & components
    background.js    → solar system + dev canvas
    music-player.js  → Sahiba player + bhangra dancer
    app.js           → theme picker, nav, counters, services
    assets/          → PNG posters & brand logos
```

## Deploy

Push to `webTargetPorfolio` — `.github/workflows/deploy.yml` exports with Kobweb and publishes to GitHub Pages (`shashankranjan.in`).
