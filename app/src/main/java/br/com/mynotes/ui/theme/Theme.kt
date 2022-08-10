package br.com.mynotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import br.com.mynotes.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
internal fun lightColors() = androidx.compose.material.lightColors(
    primary = colorResource(id = R.color.primary_light),
    primaryVariant = colorResource(id = R.color.primary_variant_light),
    onPrimary = colorResource(id = R.color.on_primary_light),
    secondary = colorResource(id = R.color.secondary_light),
    secondaryVariant = colorResource(id = R.color.secondary_variant_light),
    onSecondary = colorResource(id = R.color.on_secondary_light),
    error = colorResource(id = R.color.error),
    onError = colorResource(id = R.color.on_error)
)

@Composable
internal fun darkColors() = androidx.compose.material.darkColors(
    primary = colorResource(id = R.color.primary_dark),
    primaryVariant = colorResource(id = R.color.primary_variant_dark),
    onPrimary = colorResource(id = R.color.on_primary_dark),
    secondary = colorResource(id = R.color.secondary_dark),
    secondaryVariant = colorResource(id = R.color.secondary_variant_dark),
    onSecondary = colorResource(id = R.color.on_secondary_dark),
    error = colorResource(id = R.color.error),
    onError = colorResource(id = R.color.on_error)
)

@Composable
fun MyNotesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        systemUiController.apply {
            setStatusBarColor(
                color = colorResource(id = R.color.dark_status_bar)
            )
        }
        darkColors()
    } else {
        systemUiController.apply {
            setStatusBarColor(
                color = colorResource(id = R.color.light_status_bar)
            )
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