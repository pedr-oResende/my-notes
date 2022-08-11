package br.com.mynotes.features.notes.presentation.compose.widgets

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun TopBarIcon(
    onClick: () -> Unit,
    icon: Painter,
    visibility: Boolean,
    color: Color = MaterialTheme.colors.onPrimary
) {
    if (visibility) {
        IconButton(
            onClick = onClick
        ) {
            Icon(
                painter = icon,
                tint = color,
                contentDescription = null)
        }
    }
}