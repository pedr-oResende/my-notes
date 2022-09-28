package br.com.mynotes.features.notes.ui.compose.widgets

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TopBarIcon(
    onClick: () -> Unit,
    imageVector: ImageVector,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = imageVector,
            tint = color,
            contentDescription = null
        )
    }
}