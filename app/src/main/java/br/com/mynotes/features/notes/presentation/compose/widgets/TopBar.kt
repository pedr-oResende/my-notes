package br.com.mynotes.features.notes.presentation.compose.widgets

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable

@Composable
fun TopBar(
    title: String? = null,
    backPressedDispatcher: OnBackPressedDispatcher,
    actions: @Composable RowScope.() -> Unit = {},
    hasNavigationIcon: Boolean = true
) {
    TopAppBar(
        title = {
            if (title != null)
                Text(text = title)
        },
        navigationIcon = if (hasNavigationIcon) {
            {
                IconButton(onClick = { backPressedDispatcher.onBackPressed() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        } else null,
        actions = actions
    )
}