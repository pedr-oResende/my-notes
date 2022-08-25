package br.com.mynotes.features.notes.ui.compose.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun TopBar(
    title: String? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            if (title != null)
                Text(text = title)
        },
        navigationIcon = if (navigationIcon != null) {
            { navigationIcon() }
        } else null,
        actions = actions
    )
}