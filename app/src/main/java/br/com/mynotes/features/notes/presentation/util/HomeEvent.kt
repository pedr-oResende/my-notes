package br.com.mynotes.features.notes.presentation.util

sealed class HomeEvent {
    object Refresh : HomeEvent()
    object NoteDeleted : HomeEvent()
}
