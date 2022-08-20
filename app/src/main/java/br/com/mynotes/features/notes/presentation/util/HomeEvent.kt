package br.com.mynotes.features.notes.presentation.util

import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.screens.home.ScreenState

sealed class HomeEvent {
    data class SelectNote(val note: Note) : HomeEvent()
    data class SearchTextChanged(val text: String) : HomeEvent()
    data class ChangeScreen(val screen: ScreenState) : HomeEvent()
    data class ArchiveNote(val archive: Boolean) : HomeEvent()
    object ClearTrashCan : HomeEvent()
    object MoveNoteToTrashCan : HomeEvent()
    object DeleteNotes : HomeEvent()
    object RestoreFromTrashCan : HomeEvent()
    object RestoreNotes : HomeEvent()
    object ToggleListView : HomeEvent()
    object ToggleCloseSelection : HomeEvent()
    object ToggleMarkPin : HomeEvent()
    object ToggleMenuMore : HomeEvent()
    object OnNoteDeleted : HomeEvent()
}
