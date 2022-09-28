package br.com.mynotes.features.notes.ui.compose.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    SmallTopAppBar(
        title = {
            if (title != null)
                Text(text = title)
        },
        navigationIcon = {
            if (navigationIcon != null)
                navigationIcon()
        },
        actions = actions
    )
}