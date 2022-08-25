package br.com.mynotes.features.notes.ui.screens.note_detail.state

sealed class NotesDetailActions {
    object ProcessNote : NotesDetailActions()
    object DiscardNote : NotesDetailActions()
    object EmptyNote : NotesDetailActions()
    data class ShowRestoreNoteSnackBar(val text: String, val label: String): NotesDetailActions()
}