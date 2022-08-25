package br.com.mynotes.features.notes.ui.model

import androidx.compose.ui.graphics.vector.ImageVector
import br.com.mynotes.features.notes.ui.screens.main.state.ScreenState

data class MenuItem(
    val screen: ScreenState,
    val title: String,
    val icon: ImageVector
)