package br.com.mynotes.features.notes.presentation.compose.widgets

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable

@Composable
fun TopBar(
    title: String,
    backPressedDispatcher: OnBackPressedDispatcher,
    actions: @Composable RowScope.() -> Unit = {},
    hasNavigationIcon: Boolean = true
) {
    TopAppBar(
        title = { Text(text = title) },
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