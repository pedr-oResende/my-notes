package br.com.mynotes.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

val LightColors = lightColors(
    primary = Navy700,
    primaryVariant = Navy900,
    secondary = Green300,
    secondaryVariant = Green900
)

val DarkColors = darkColors(
    primary = Navy500,
    primaryVariant = Navy900,
    secondary = Green300
)

@Composable
fun MyNotesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}