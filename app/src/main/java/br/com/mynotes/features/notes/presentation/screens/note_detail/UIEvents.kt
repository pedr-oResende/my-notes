package br.com.mynotes.features.notes.presentation.screens.note_detail

sealed class UIEvents {
    object ProcessNote : UIEvents()
    data class ShowSnackBar(val message: String): UIEvents()
}