package br.com.mynotes.features.notes.presentation.screens.home

sealed class UIEvents {
    data class ShowSnackBar(val message: String): UIEvents()
    data class ShowUndoSnackBar(val label:String, val text: String): UIEvents()
}