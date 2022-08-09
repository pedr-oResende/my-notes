package br.com.mynotes.compose.widgets

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TopBar(
    title: String,
    backPressedDispatcher: OnBackPressedDispatcher,
    action: (() -> Unit)? = null,
    actionIcon: ImageVector? = null,
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
        actions = {
            if (action != null && actionIcon != null) {
                IconButton(onClick = action) {
                    Icon(actionIcon, contentDescription = null)
                }
            }
        }
    )
}