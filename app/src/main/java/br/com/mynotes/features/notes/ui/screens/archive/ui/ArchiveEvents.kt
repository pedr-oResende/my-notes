package br.com.mynotes.features.notes.ui.screens.archive.ui

sealed class ArchiveEvents {
    object UnArchiveNote : ArchiveEvents()
    object MoveNoteToTrashCan : ArchiveEvents()
    object RestoreNotes : ArchiveEvents()
    object ToggleMarkPin : ArchiveEvents()
}