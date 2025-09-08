package com.arshtraders.fieldtracker.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2196F3),        // Blue
    onPrimary = Color(0xFFFFFFFF),      // White
    primaryContainer = Color(0xFFBBDEFB), // Light Blue
    onPrimaryContainer = Color(0xFF0D47A1), // Dark Blue
    
    secondary = Color(0xFF4CAF50),      // Green
    onSecondary = Color(0xFFFFFFFF),    // White
    secondaryContainer = Color(0xFFC8E6C9), // Light Green
    onSecondaryContainer = Color(0xFF1B5E20), // Dark Green
    
    tertiary = Color(0xFFFF9800),       // Orange
    onTertiary = Color(0xFFFFFFFF),     // White
    
    error = Color(0xFFF44336),          // Red
    onError = Color(0xFFFFFFFF),        // White
    
    background = Color(0xFFF5F5F5),     // Light Gray
    onBackground = Color(0xFF212121),   // Dark Gray
    
    surface = Color(0xFFFFFFFF),        // White
    onSurface = Color(0xFF212121),      // Dark Gray
    
    surfaceVariant = Color(0xFFE0E0E0), // Medium Gray
    onSurfaceVariant = Color(0xFF424242) // Medium Dark Gray
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),        // Light Blue
    onPrimary = Color(0xFF0D47A1),      // Dark Blue
    primaryContainer = Color(0xFF1976D2), // Medium Blue
    onPrimaryContainer = Color(0xFFBBDEFB), // Light Blue
    
    secondary = Color(0xFF81C784),      // Light Green
    onSecondary = Color(0xFF1B5E20),    // Dark Green
    secondaryContainer = Color(0xFF388E3C), // Medium Green
    onSecondaryContainer = Color(0xFFC8E6C9), // Light Green
    
    tertiary = Color(0xFFFFB74D),       // Light Orange
    onTertiary = Color(0xFFE65100),     // Dark Orange
    
    error = Color(0xFFEF5350),          // Light Red
    onError = Color(0xFFB71C1C),        // Dark Red
    
    background = Color(0xFF121212),     // Dark Gray
    onBackground = Color(0xFFE0E0E0),   // Light Gray
    
    surface = Color(0xFF1E1E1E),        // Dark Surface
    onSurface = Color(0xFFE0E0E0),      // Light Gray
    
    surfaceVariant = Color(0xFF424242), // Medium Gray
    onSurfaceVariant = Color(0xFFBDBDBD) // Light Medium Gray
)

@Composable
fun FieldTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}