package br.com.mynotes.features.notes.ui.model

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)