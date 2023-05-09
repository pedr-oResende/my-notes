package br.com.mynotes.features.notes.presentation.screens.archive.ui

sealed class ArchiveEvents {
    object UnArchiveNote : ArchiveEvents()
    object MoveNoteToTrashCan : ArchiveEvents()
    object RestoreNotes : ArchiveEvents()
    object ToggleMarkPin : ArchiveEvents()
}