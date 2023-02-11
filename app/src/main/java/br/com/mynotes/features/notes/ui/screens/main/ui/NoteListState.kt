package br.com.mynotes.features.notes.ui.screens.main.ui

enum class NoteListState {
    Grid, Linear;

    fun switch(): NoteListState {
        return when (this) {
            Grid -> Linear
            Linear -> Grid
        }

    }
}