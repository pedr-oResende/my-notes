package br.com.mynotes.features.notes.presentation.screens.archive.ui

sealed class ArchiveEvents {
    data object UnArchiveNote : ArchiveEvents()
    data object MoveNoteToTrashCan : ArchiveEvents()
    data object RestoreNotes : ArchiveEvents()
    data object ToggleMarkPin : ArchiveEvents()
}