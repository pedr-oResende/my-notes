package br.com.mynotes.features.notes.presentation.util

sealed class NotesEvent {
    data class SelectNote(val noteId: Int?): NotesEvent()
    object DeleteNote: NotesEvent()
    object ArchiveNote: NotesEvent()
    object RestoreNotes: NotesEvent()
    object PinNote: NotesEvent()
    object ToggleListView: NotesEvent()
    data class ToggleCloseSelection(val cleanList: Boolean): NotesEvent()
    object ToggleMarkPin : NotesEvent()
    object OnNoteDeleted : NotesEvent()
}
