package com.github.juanncode.softtekmovies.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    primary = MovieBlue,
    background = MovieBlack,
    surface = MovieDarkGray,
    secondary = MovieWhite,
    tertiary = MovieWhite,
    primaryContainer = MovieBlue30,
    onPrimary = MovieBlack,
    onBackground = MovieWhite,
    onSurface = MovieWhite,
    onSurfaceVariant = MovieGray,
    error = MovieDarkRed,
    errorContainer = MovieDarkRed5
)

@Composable
fun SofttekMoviesTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}