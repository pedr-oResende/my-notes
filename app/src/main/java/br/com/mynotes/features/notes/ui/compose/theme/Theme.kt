package br.com.mynotes.features.notes.ui.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import br.com.mynotes.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MyNotesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        systemUiController.apply {
            setStatusBarColor(color = darkColors().background)
            setNavigationBarColor(color = darkColors().surface)
        }
        darkColors()
    } else {
        systemUiController.apply {
            setStatusBarColor(color = colorResource(id = R.color.status_bar))
            setNavigationBarColor(color = lightColors().surface)
        }
        lightColors()
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}