package com.shashank.portfolio.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/** Portfolio visual modes — Wasm-safe labels (no emoji; they render as broken glyphs on web). */
enum class PortfolioThemeMode(
    val label: String,
    val shortLabel: String,
    val accentColor: Color,
) {
    Midnight("Midnight", "Night", Color(0xFF3B82F6)),
    Light("Light", "Light", Color(0xFF2563EB)),
    Android("Android", "Droid", AndroidGreen),
    Ocean("Ocean", "Ocean", Color(0xFF06B6D4)),
    Sunset("Sunset", "Sunset", Color(0xFFF97316)),
    Cyber("Cyber", "Cyber", Color(0xFF00FF88)),
    Amoled("AMOLED", "AMOLED", Color(0xFFBB86FC)),
    Forest("Forest", "Forest", Color(0xFF22C55E)),
    Rose("Rose", "Rose", Color(0xFFF43F5E)),
    Lavender("Lavender", "Lav", Color(0xFFA78BFA)),
    Gold("Gold", "Gold", Color(0xFFEAB308)),
    Crimson("Crimson", "Crim", Color(0xFFDC2626)),
    Mint("Mint", "Mint", Color(0xFF2DD4BF)),
    Slate("Slate", "Slate", Color(0xFF94A3B8)),
    NeonPink("Neon", "Neon", Color(0xFFFF2D95)),
    Arctic("Arctic", "Arctic", Color(0xFF38BDF8)),
    Ember("Ember", "Ember", Color(0xFFFB7185)),
}

data class ThemePalette(
    val colorScheme: ColorScheme,
    val extended: ExtendedColors,
    val accentGlow: Color,
    val particleColor: Color,
)

fun paletteFor(mode: PortfolioThemeMode): ThemePalette = when (mode) {
    PortfolioThemeMode.Midnight -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = Color(0xFF3B82F6), secondary = Color(0xFFA78BFA),
            background = Color(0xFF09090B), surface = Color(0xFF18181B),
            onBackground = Color(0xFFFAFAFA), onSurface = Color(0xFFF4F4F5),
            surfaceVariant = Color(0xFF27272A), outline = Color(0xFF27272A),
        ),
        extended = ext(Color(0xFFA1A1AA), Color(0xFF34D399), Color(0xFF27272A), Color(0xFF27272A),
            Color(0xFF3B82F6), Color(0xFFA78BFA), Color(0xFF09090B), Color(0xFFFAFAFA)),
        accentGlow = Color(0xFF3B82F6), particleColor = Color(0xFF3B82F6),
    )
    PortfolioThemeMode.Light -> ThemePalette(
        colorScheme = lightColorScheme(
            primary = Color(0xFF2563EB), secondary = Color(0xFF7C3AED),
            background = Color(0xFFFAFAFA), surface = Color(0xFFFFFFFF),
            onBackground = Color(0xFF09090B), onSurface = Color(0xFF18181B),
            surfaceVariant = Color(0xFFF4F4F5), outline = Color(0xFFE4E4E7),
        ),
        extended = ext(Color(0xFF71717A), Color(0xFF059669), Color(0xFFE4E4E7), Color(0xFFF4F4F5),
            Color(0xFF2563EB), Color(0xFF7C3AED), Color(0xFFFAFAFA), Color(0xFF09090B)),
        accentGlow = Color(0xFF2563EB), particleColor = Color(0xFF7C3AED),
    )
    PortfolioThemeMode.Android -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = AndroidGreen, secondary = Color(0xFF4285F4),
            background = Color(0xFF0D1117), surface = Color(0xFF161B22),
            onBackground = Color(0xFFE6EDF3), onSurface = Color(0xFFE6EDF3),
            surfaceVariant = Color(0xFF21262D), outline = Color(0xFF30363D),
        ),
        extended = ext(Color(0xFF8B949E), AndroidGreen, Color(0xFF30363D), Color(0xFF21262D),
            AndroidGreen, Color(0xFF4285F4), Color(0xFF0D1117), Color(0xFFE6EDF3)),
        accentGlow = AndroidGreen, particleColor = AndroidGreen,
    )
    PortfolioThemeMode.Ocean -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = Color(0xFF06B6D4), secondary = Color(0xFF0EA5E9),
            background = Color(0xFF0A1628), surface = Color(0xFF0F2140),
            onBackground = Color(0xFFE0F2FE), onSurface = Color(0xFFE0F2FE),
            surfaceVariant = Color(0xFF1A365D), outline = Color(0xFF1E4976),
        ),
        extended = ext(Color(0xFF7DD3FC), Color(0xFF22D3EE), Color(0xFF1E4976), Color(0xFF1A365D),
            Color(0xFF06B6D4), Color(0xFF0EA5E9), Color(0xFF0A1628), Color(0xFFE0F2FE)),
        accentGlow = Color(0xFF06B6D4), particleColor = Color(0xFF22D3EE),
    )
    PortfolioThemeMode.Sunset -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = Color(0xFFF97316), secondary = Color(0xFFEC4899),
            background = Color(0xFF1A0A14), surface = Color(0xFF2D1520),
            onBackground = Color(0xFFFFF1F2), onSurface = Color(0xFFFFF1F2),
            surfaceVariant = Color(0xFF3D1F2E), outline = Color(0xFF4A2540),
        ),
        extended = ext(Color(0xFFFDA4AF), Color(0xFFFB923C), Color(0xFF4A2540), Color(0xFF3D1F2E),
            Color(0xFFF97316), Color(0xFFEC4899), Color(0xFF1A0A14), Color(0xFFFFF1F2)),
        accentGlow = Color(0xFFF97316), particleColor = Color(0xFFEC4899),
    )
    PortfolioThemeMode.Cyber -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = Color(0xFF00FF88), secondary = Color(0xFFFF00FF),
            background = Color(0xFF050508), surface = Color(0xFF0A0A12),
            onBackground = Color(0xFFE0FFE8), onSurface = Color(0xFFE0FFE8),
            surfaceVariant = Color(0xFF12121F), outline = Color(0xFF1F1F35),
        ),
        extended = ext(Color(0xFF88FFAA), Color(0xFF00FF88), Color(0xFF1F1F35), Color(0xFF12121F),
            Color(0xFF00FF88), Color(0xFFFF00FF), Color(0xFF050508), Color(0xFFE0FFE8)),
        accentGlow = Color(0xFF00FF88), particleColor = Color(0xFFFF00FF),
    )
    PortfolioThemeMode.Amoled -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = Color(0xFFBB86FC), secondary = Color(0xFF03DAC6),
            background = Color(0xFF000000), surface = Color(0xFF0A0A0A),
            onBackground = Color(0xFFFFFFFF), onSurface = Color(0xFFE0E0E0),
            surfaceVariant = Color(0xFF141414), outline = Color(0xFF1F1F1F),
        ),
        extended = ext(Color(0xFF9E9E9E), Color(0xFF03DAC6), Color(0xFF1F1F1F), Color(0xFF141414),
            Color(0xFFBB86FC), Color(0xFF03DAC6), Color(0xFF000000), Color(0xFFFFFFFF)),
        accentGlow = Color(0xFFBB86FC), particleColor = Color(0xFF03DAC6),
    )
    PortfolioThemeMode.Forest -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = Color(0xFF22C55E), secondary = Color(0xFF84CC16),
            background = Color(0xFF071209), surface = Color(0xFF0F1F14),
            onBackground = Color(0xFFECFDF5), onSurface = Color(0xFFD1FAE5),
            surfaceVariant = Color(0xFF14532D), outline = Color(0xFF166534),
        ),
        extended = ext(Color(0xFF86EFAC), Color(0xFF22C55E), Color(0xFF166534), Color(0xFF14532D),
            Color(0xFF22C55E), Color(0xFF84CC16), Color(0xFF071209), Color(0xFFECFDF5)),
        accentGlow = Color(0xFF22C55E), particleColor = Color(0xFF84CC16),
    )
    PortfolioThemeMode.Rose -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = Color(0xFFF43F5E), secondary = Color(0xFFFB7185),
            background = Color(0xFF1A0A10), surface = Color(0xFF2A121C),
            onBackground = Color(0xFFFFF1F2), onSurface = Color(0xFFFFE4E6),
            surfaceVariant = Color(0xFF4C0519), outline = Color(0xFF881337),
        ),
        extended = ext(Color(0xFFFDA4AF), Color(0xFFF43F5E), Color(0xFF881337), Color(0xFF4C0519),
            Color(0xFFF43F5E), Color(0xFFFB7185), Color(0xFF1A0A10), Color(0xFFFFF1F2)),
        accentGlow = Color(0xFFF43F5E), particleColor = Color(0xFFFB7185),
    )
    PortfolioThemeMode.Lavender -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = Color(0xFFA78BFA), secondary = Color(0xFFC4B5FD),
            background = Color(0xFF100C1A), surface = Color(0xFF1A1428),
            onBackground = Color(0xFFF5F3FF), onSurface = Color(0xFFEDE9FE),
            surfaceVariant = Color(0xFF2E1065), outline = Color(0xFF4C1D95),
        ),
        extended = ext(Color(0xFFC4B5FD), Color(0xFFA78BFA), Color(0xFF4C1D95), Color(0xFF2E1065),
            Color(0xFFA78BFA), Color(0xFFC4B5FD), Color(0xFF100C1A), Color(0xFFF5F3FF)),
        accentGlow = Color(0xFFA78BFA), particleColor = Color(0xFFC4B5FD),
    )
    PortfolioThemeMode.Gold -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = Color(0xFFEAB308), secondary = Color(0xFFFACC15),
            background = Color(0xFF141008), surface = Color(0xFF1F1A0C),
            onBackground = Color(0xFFFEFCE8), onSurface = Color(0xFFFEF9C3),
            surfaceVariant = Color(0xFF422006), outline = Color(0xFF713F12),
        ),
        extended = ext(Color(0xFFFDE047), Color(0xFFEAB308), Color(0xFF713F12), Color(0xFF422006),
            Color(0xFFEAB308), Color(0xFFFACC15), Color(0xFF141008), Color(0xFFFEFCE8)),
        accentGlow = Color(0xFFEAB308), particleColor = Color(0xFFFACC15),
    )
    PortfolioThemeMode.Crimson -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = Color(0xFFDC2626), secondary = Color(0xFFEF4444),
            background = Color(0xFF140808), surface = Color(0xFF1F0E0E),
            onBackground = Color(0xFFFEF2F2), onSurface = Color(0xFFFEE2E2),
            surfaceVariant = Color(0xFF450A0A), outline = Color(0xFF7F1D1D),
        ),
        extended = ext(Color(0xFFFCA5A5), Color(0xFFDC2626), Color(0xFF7F1D1D), Color(0xFF450A0A),
            Color(0xFFDC2626), Color(0xFFEF4444), Color(0xFF140808), Color(0xFFFEF2F2)),
        accentGlow = Color(0xFFDC2626), particleColor = Color(0xFFEF4444),
    )
    PortfolioThemeMode.Mint -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = Color(0xFF2DD4BF), secondary = Color(0xFF5EEAD4),
            background = Color(0xFF061412), surface = Color(0xFF0C2220),
            onBackground = Color(0xFFCCFBF1), onSurface = Color(0xFF99F6E4),
            surfaceVariant = Color(0xFF134E4A), outline = Color(0xFF115E59),
        ),
        extended = ext(Color(0xFF5EEAD4), Color(0xFF2DD4BF), Color(0xFF115E59), Color(0xFF134E4A),
            Color(0xFF2DD4BF), Color(0xFF5EEAD4), Color(0xFF061412), Color(0xFFCCFBF1)),
        accentGlow = Color(0xFF2DD4BF), particleColor = Color(0xFF5EEAD4),
    )
    PortfolioThemeMode.Slate -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = Color(0xFF94A3B8), secondary = Color(0xFFCBD5E1),
            background = Color(0xFF0C0F14), surface = Color(0xFF151A22),
            onBackground = Color(0xFFF1F5F9), onSurface = Color(0xFFE2E8F0),
            surfaceVariant = Color(0xFF1E293B), outline = Color(0xFF334155),
        ),
        extended = ext(Color(0xFF94A3B8), Color(0xFF64748B), Color(0xFF334155), Color(0xFF1E293B),
            Color(0xFF94A3B8), Color(0xFFCBD5E1), Color(0xFF0C0F14), Color(0xFFF1F5F9)),
        accentGlow = Color(0xFF94A3B8), particleColor = Color(0xFFCBD5E1),
    )
    PortfolioThemeMode.NeonPink -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = Color(0xFFFF2D95), secondary = Color(0xFFFF6BCE),
            background = Color(0xFF0A0008), surface = Color(0xFF150010),
            onBackground = Color(0xFFFFF0FA), onSurface = Color(0xFFFFE0F5),
            surfaceVariant = Color(0xFF3D0028), outline = Color(0xFF5C003D),
        ),
        extended = ext(Color(0xFFFF8AD8), Color(0xFFFF2D95), Color(0xFF5C003D), Color(0xFF3D0028),
            Color(0xFFFF2D95), Color(0xFFFF6BCE), Color(0xFF0A0008), Color(0xFFFFF0FA)),
        accentGlow = Color(0xFFFF2D95), particleColor = Color(0xFFFF6BCE),
    )
    PortfolioThemeMode.Arctic -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = Color(0xFF38BDF8), secondary = Color(0xFF7DD3FC),
            background = Color(0xFF050D14), surface = Color(0xFF0C1824),
            onBackground = Color(0xFFF0F9FF), onSurface = Color(0xFFE0F2FE),
            surfaceVariant = Color(0xFF0C4A6E), outline = Color(0xFF075985),
        ),
        extended = ext(Color(0xFF7DD3FC), Color(0xFF38BDF8), Color(0xFF075985), Color(0xFF0C4A6E),
            Color(0xFF38BDF8), Color(0xFF7DD3FC), Color(0xFF050D14), Color(0xFFF0F9FF)),
        accentGlow = Color(0xFF38BDF8), particleColor = Color(0xFF7DD3FC),
    )
    PortfolioThemeMode.Ember -> ThemePalette(
        colorScheme = darkColorScheme(
            primary = Color(0xFFFB7185), secondary = Color(0xFFF97316),
            background = Color(0xFF120A08), surface = Color(0xFF1E1210),
            onBackground = Color(0xFFFFF7ED), onSurface = Color(0xFFFFEDD5),
            surfaceVariant = Color(0xFF431407), outline = Color(0xFF7C2D12),
        ),
        extended = ext(Color(0xFFFDBA74), Color(0xFFFB7185), Color(0xFF7C2D12), Color(0xFF431407),
            Color(0xFFFB7185), Color(0xFFF97316), Color(0xFF120A08), Color(0xFFFFF7ED)),
        accentGlow = Color(0xFFFB7185), particleColor = Color(0xFFF97316),
    )
}

private fun ext(
    muted: Color, accent: Color, border: Color, surfaceElevated: Color,
    gradStart: Color, gradEnd: Color, bg: Color, onBg: Color,
) = ExtendedColors(
    muted = muted, accent = accent, glass = surfaceElevated.copy(alpha = 0.88f),
    border = border, surfaceElevated = surfaceElevated,
    gradientBrush = Brush.linearGradient(listOf(gradStart, gradEnd)),
    nameGradientBrush = Brush.linearGradient(listOf(onBg, gradStart, gradEnd)),
    cardGradient = Brush.linearGradient(listOf(surfaceElevated.copy(alpha = 0.7f), bg)),
)
