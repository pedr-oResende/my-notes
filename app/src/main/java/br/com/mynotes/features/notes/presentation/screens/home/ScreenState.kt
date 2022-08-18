package br.com.mynotes.features.notes.presentation.screens.home

sealed class ScreenState {
    object HomeScreen: ScreenState()
    object ArchiveScreen: ScreenState()
    object TrashCanScreen: ScreenState()
}