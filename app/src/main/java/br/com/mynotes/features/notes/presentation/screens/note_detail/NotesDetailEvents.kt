package br.com.mynotes.features.notes.presentation.screens.note_detail

sealed class NotesDetailEvents {
    object ProcessNote : NotesDetailEvents()
    object DiscardNote : NotesDetailEvents()
    object EmptyNote : NotesDetailEvents()
    data class ShowSnackBar(val message: String): NotesDetailEvents()
}