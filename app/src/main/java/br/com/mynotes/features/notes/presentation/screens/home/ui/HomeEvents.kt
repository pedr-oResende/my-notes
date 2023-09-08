package br.com.mynotes.features.notes.presentation.screens.home.ui

sealed class HomeEvents {
    data object ArchiveNotes : HomeEvents()
    data object MoveNoteToTrashCan : HomeEvents()
    data object RestoreNotes : HomeEvents()
    data object ToggleMarkPin : HomeEvents()
}