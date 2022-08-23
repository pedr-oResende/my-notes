package br.com.mynotes.features.notes.presentation.util

import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.screens.home.ScreenState

sealed class HomeUIEvents {
    data class SelectNote(val note: Note) : HomeUIEvents()
    data class SearchTextChanged(val text: String) : HomeUIEvents()
    data class ChangeScreen(val screen: ScreenState) : HomeUIEvents()
    data class ArchiveNote(val archive: Boolean) : HomeUIEvents()
    object ClearTrashCan : HomeUIEvents()
    object MoveNoteToTrashCan : HomeUIEvents()
    object DeleteNotes : HomeUIEvents()
    object RestoreFromTrashCan : HomeUIEvents()
    object RestoreNotes : HomeUIEvents()
    object ToggleListView : HomeUIEvents()
    object ToggleCloseSelection : HomeUIEvents()
    object ToggleMarkPin : HomeUIEvents()
    object ToggleMenuMore : HomeUIEvents()
    object OnNoteDeleted : HomeUIEvents()
}
