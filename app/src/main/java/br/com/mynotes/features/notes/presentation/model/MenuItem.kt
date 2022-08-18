package br.com.mynotes.features.notes.presentation.model

import androidx.compose.ui.graphics.vector.ImageVector
import br.com.mynotes.features.notes.presentation.screens.home.ScreenState

data class MenuItem(
    val screen: ScreenState,
    val title: String,
    val icon: ImageVector
)