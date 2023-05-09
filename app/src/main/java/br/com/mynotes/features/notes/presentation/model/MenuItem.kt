package br.com.mynotes.features.notes.presentation.model

import androidx.compose.ui.graphics.vector.ImageVector
import br.com.mynotes.features.notes.presentation.screens.main.ui.DrawerScreens

data class MenuItem(
    val screen: DrawerScreens,
    val title: String,
    val icon: ImageVector
)