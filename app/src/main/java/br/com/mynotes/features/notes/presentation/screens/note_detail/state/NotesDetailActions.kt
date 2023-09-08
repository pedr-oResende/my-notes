package br.com.mynotes.features.notes.presentation.screens.note_detail.state

sealed class NotesDetailActions {
    data object ProcessNote : NotesDetailActions()
    data object DiscardNote : NotesDetailActions()
    data object EmptyNote : NotesDetailActions()
    data class ShowRestoreNoteSnackBar(val text: String, val label: String): NotesDetailActions()
}