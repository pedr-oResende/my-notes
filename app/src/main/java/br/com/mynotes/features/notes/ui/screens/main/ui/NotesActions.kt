package br.com.mynotes.features.notes.ui.screens.main.ui

sealed class NotesActions {
    data class ShowSnackBar(val message: String): NotesActions()
    data class ShowUndoSnackBar(val label:String, val text: String): NotesActions()
}