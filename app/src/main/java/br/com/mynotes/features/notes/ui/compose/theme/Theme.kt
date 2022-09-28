package br.com.mynotes.features.notes.ui.compose.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorPalette = darkColorScheme(
    primary = Yellow80,
    onPrimary = Yellow20,
    primaryContainer = Yellow30,
    onPrimaryContainer = Yellow90,
    inversePrimary = Yellow40,
    secondary = Orange80,
    onSecondary = Orange20,
    secondaryContainer = Orange30,
    onSecondaryContainer = Orange90,
    tertiary = Green80,
    onTertiary = Green20,
    tertiaryContainer = Green30,
    onTertiaryContainer = Green90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Gray10,
    onBackground = Gray90,
    surface = YellowGray30,
    onSurface = YellowGray80,
    inverseSurface = Gray90,
    inverseOnSurface = Gray10,
    surfaceVariant = YellowGray30,
    onSurfaceVariant = YellowGray80,
    outline = YellowGray80
)

private val LightColorPalette = lightColorScheme(
    primary = Yellow40,
    onPrimary = Color.White,
    primaryContainer = Yellow90,
    onPrimaryContainer = Yellow10,
    inversePrimary = Yellow80,
    secondary = Orange40,
    onSecondary = Color.White,
    secondaryContainer = Orange90,
    onSecondaryContainer = Orange10,
    tertiary = Green40,
    onTertiary = Color.White,
    tertiaryContainer = Green90,
    onTertiaryContainer = Green10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red90,
    background = Gray99,
    onBackground = Gray10,
    surface = YellowGray90,
    onSurface = YellowGray30,
    inverseSurface = Gray10,
    inverseOnSurface = Gray99,
    surfaceVariant = YellowGray30,
    onSurfaceVariant = YellowGray80,
    outline = YellowGray80
)
@Composable
fun MyNotesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val useDynamicColors = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colorScheme = when {
        useDynamicColors && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
        useDynamicColors && !darkTheme -> dynamicDarkColorScheme(LocalContext.current)
        darkTheme -> DarkColorPalette
        else -> LightColorPalette
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}