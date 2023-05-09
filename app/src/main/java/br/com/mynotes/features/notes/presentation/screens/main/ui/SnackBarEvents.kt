package br.com.mynotes.features.notes.presentation.screens.main.ui

sealed class SnackBarEvents {
    data class ShowSnackBar(val message: String) : SnackBarEvents()
    data class ShowUndoSnackBar(val label: String, val message: String) : SnackBarEvents()
}