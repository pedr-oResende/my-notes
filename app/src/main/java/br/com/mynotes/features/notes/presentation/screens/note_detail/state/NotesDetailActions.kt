package br.com.mynotes.features.notes.presentation.screens.note_detail.state

sealed class NotesDetailActions {
    object ProcessNote : NotesDetailActions()
    object DiscardNote : NotesDetailActions()
    object EmptyNote : NotesDetailActions()
    data class ShowRestoreNoteSnackBar(val text: String, val label: String): NotesDetailActions()
}