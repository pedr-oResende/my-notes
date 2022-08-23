package br.com.mynotes.features.notes.presentation.screens.note_detail

sealed class NotesDetailEvents {
    object ProcessNote : NotesDetailEvents()
    object DiscardNote : NotesDetailEvents()
    object EmptyNote : NotesDetailEvents()
    data class ShowRestoreNoteSnackBar(val text: String, val label: String): NotesDetailEvents()
}