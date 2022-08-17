package br.com.mynotes.features.notes.presentation.screens.home

sealed class NotesEvents {
    data class ShowSnackBar(val message: String): NotesEvents()
    data class ShowUndoSnackBar(val label:String, val text: String): NotesEvents()
}