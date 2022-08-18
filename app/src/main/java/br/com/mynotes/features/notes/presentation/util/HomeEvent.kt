package br.com.mynotes.features.notes.presentation.util

import br.com.mynotes.features.notes.domain.model.Note

sealed class HomeEvent {
    data class SelectNote(val note: Note): HomeEvent()
    data class SearchTextChanged(val text: String) : HomeEvent()
    object DeleteNote: HomeEvent()
    object ArchiveNote: HomeEvent()
    object RestoreNotes: HomeEvent()
    object ToggleListView: HomeEvent()
    object ToggleCloseSelection: HomeEvent()
    object ToggleMarkPin : HomeEvent()
    object ToggleMenuMore : HomeEvent()
    object OnNoteDeleted : HomeEvent()
}
