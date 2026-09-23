package com.example.rrgbs.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = CrimsonRed,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFEBEE),
    onPrimaryContainer = CrimsonRedDark,
    secondary = DeepNavy,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE2E8F0),
    onSecondaryContainer = DeepNavy,
    tertiary = AccentAmber,
    onTertiary = Color.White,
    background = BackgroundLight,
    onBackground = DeepNavy,
    surface = SurfaceLight,
    onSurface = DeepNavy,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = SlateNavy,
    outline = OutlineLight
)

private val DarkColorScheme = darkColorScheme(
    primary = CrimsonRedLight,
    onPrimary = Color.Black,
    primaryContainer = CrimsonRedDark,
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF94A3B8),
    onSecondary = Color.Black,
    background = Color(0xFF0F172A),
    onBackground = Color.White,
    surface = Color(0xFF1E293B),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF475569)
)

@Composable
fun RRGBSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
