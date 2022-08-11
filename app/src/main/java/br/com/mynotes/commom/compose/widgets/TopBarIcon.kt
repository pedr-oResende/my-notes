package br.com.mynotes.commom.compose.widgets

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TopBarIcon(
    onClick: () -> Unit,
    painter: Painter? = null,
    imageVector: ImageVector? = null,
    color: Color = MaterialTheme.colors.onSurface
) {
    IconButton(
        onClick = onClick
    ) {
        if (painter != null) {
            Icon(
                painter = painter,
                tint = color,
                contentDescription = null
            )
        } else if (imageVector != null) {
            Icon(
                imageVector = imageVector,
                tint = color,
                contentDescription = null
            )
        }
    }
}