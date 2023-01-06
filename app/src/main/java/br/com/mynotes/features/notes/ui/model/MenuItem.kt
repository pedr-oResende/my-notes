package br.com.mynotes.features.notes.ui.model

import androidx.compose.ui.graphics.vector.ImageVector
import br.com.mynotes.features.notes.ui.screens.main.ui.DrawerScreens

data class MenuItem(
    val screen: DrawerScreens,
    val title: String,
    val icon: ImageVector
)