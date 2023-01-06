package br.com.mynotes.features.notes.ui.screens.main.ui

import br.com.mynotes.features.notes.domain.model.Note

sealed class MainUIEvents {
    data class SelectNote(val note: Note) : MainUIEvents()
    data class SearchTextChanged(val text: String) : MainUIEvents()
    object ToggleCloseSelection : MainUIEvents()
}
