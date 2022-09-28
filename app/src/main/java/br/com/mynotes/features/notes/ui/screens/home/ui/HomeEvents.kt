package br.com.mynotes.features.notes.ui.screens.home.ui

sealed class HomeEvents {
    object ArchiveNote : HomeEvents()
    object MoveNoteToTrashCan : HomeEvents()
    object RestoreNotes : HomeEvents()
    object ToggleMarkPin : HomeEvents()
}