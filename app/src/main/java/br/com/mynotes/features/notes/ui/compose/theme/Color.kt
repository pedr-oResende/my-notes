package br.com.mynotes.features.notes.ui.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import br.com.mynotes.R

@Composable
internal fun lightColors() = androidx.compose.material.lightColors(
    primary = colorResource(id = R.color.primary),
    primaryVariant = colorResource(id = R.color.primary_variant),
    onPrimary = colorResource(id = R.color.on_primary),
    secondary = colorResource(id = R.color.secondary),
    secondaryVariant = colorResource(id = R.color.secondary_variant),
    onSecondary = colorResource(id = R.color.on_secondary),
    background = colorResource(id = R.color.background),
    onBackground = colorResource(id = R.color.on_background),
    surface = colorResource(id = R.color.surface),
    onSurface = colorResource(id = R.color.on_surface),
    error = colorResource(id = R.color.error),
    onError = colorResource(id = R.color.on_error)
)

@Composable
internal fun darkColors() = androidx.compose.material.darkColors(
    primary = colorResource(id = R.color.primary),
    primaryVariant = colorResource(id = R.color.primary_variant),
    onPrimary = colorResource(id = R.color.on_primary),
    secondary = colorResource(id = R.color.secondary),
    secondaryVariant = colorResource(id = R.color.secondary_variant),
    onSecondary = colorResource(id = R.color.on_secondary),
    background = colorResource(id = R.color.background),
    onBackground = colorResource(id = R.color.on_background),
    surface = colorResource(id = R.color.surface),
    onSurface = colorResource(id = R.color.on_surface),
    error = colorResource(id = R.color.error),
    onError = colorResource(id = R.color.on_error)
)