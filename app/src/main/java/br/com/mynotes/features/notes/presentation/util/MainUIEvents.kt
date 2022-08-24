package br.com.mynotes.features.notes.presentation.util

import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.screens.main.ScreenState

sealed class MainUIEvents {
    data class SelectNote(val note: Note) : MainUIEvents()
    data class SearchTextChanged(val text: String) : MainUIEvents()
    data class ChangeScreen(val screen: ScreenState) : MainUIEvents()
    data class ArchiveNote(val archive: Boolean) : MainUIEvents()
    object ClearTrashCan : MainUIEvents()
    object MoveNoteToTrashCan : MainUIEvents()
    object DeleteNotes : MainUIEvents()
    object RestoreFromTrashCan : MainUIEvents()
    object RestoreNotes : MainUIEvents()
    object ToggleListView : MainUIEvents()
    object ToggleCloseSelection : MainUIEvents()
    object ToggleMarkPin : MainUIEvents()
    object ToggleMenuMore : MainUIEvents()
    object OnNoteDeleted : MainUIEvents()
    object CloseAutoDeleteMessage : MainUIEvents()
}
