package br.com.mynotes.features.notes.ui.screens.main.state

import br.com.mynotes.features.notes.domain.model.Note

sealed class MainUIEvents {
    data class SelectNote(val note: Note) : MainUIEvents()
    data class SearchTextChanged(val text: String) : MainUIEvents()
    object ToggleListView : MainUIEvents()
    object ToggleCloseSelection : MainUIEvents()
    object ToggleMenuMore : MainUIEvents()
}
