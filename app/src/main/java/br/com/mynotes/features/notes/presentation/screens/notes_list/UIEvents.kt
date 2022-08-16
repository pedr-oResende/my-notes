package br.com.mynotes.features.notes.presentation.screens.notes_list

sealed class UIEvents {
    data class ShowSnackBar(val message: String): UIEvents()
    data class ShowUndoSnackBar(val label:String, val text: String): UIEvents()
}