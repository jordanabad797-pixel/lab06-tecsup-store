package com.tecsup.tecsupstore.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaMorado = lightColorScheme(
    primary = Color(0xFF5B2D8E),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDE7F6),
    onPrimaryContainer = Color(0xFF3B1A66),
    secondaryContainer = Color(0xFFEDE7F6),
    onSecondaryContainer = Color(0xFF5B2D8E),
    background = Color.White,
    surface = Color.White,
    surfaceVariant = Color(0xFFF3EDF9),
    onSurfaceVariant = Color(0xFF49454F),
    outlineVariant = Color(0xFFE2D9EE)
)

@Composable
fun TecsupStoreTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaMorado,
        typography = Typography,
        content = content
    )
}