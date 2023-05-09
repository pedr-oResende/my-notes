package br.com.mynotes.features.notes.presentation.screens.home.ui

sealed class HomeEvents {
    object ArchiveNotes : HomeEvents()
    object MoveNoteToTrashCan : HomeEvents()
    object RestoreNotes : HomeEvents()
    object ToggleMarkPin : HomeEvents()
}