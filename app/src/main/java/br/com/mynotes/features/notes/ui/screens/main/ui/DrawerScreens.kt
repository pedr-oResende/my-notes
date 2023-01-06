package br.com.mynotes.features.notes.ui.screens.main.ui

sealed class DrawerScreens {
    object Home : DrawerScreens()
    object Archive : DrawerScreens()
    object TrashCan : DrawerScreens()
}