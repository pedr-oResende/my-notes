package br.com.mynotes.features.notes.presentation.util

import br.com.mynotes.features.notes.domain.model.Note

sealed class NotesEvent {
    data class SelectNote(val note: Note): NotesEvent()
    object DeleteNote: NotesEvent()
    object ArchiveNote: NotesEvent()
    object RestoreNotes: NotesEvent()
    object ToggleListView: NotesEvent()
    object ToggleCloseSelection: NotesEvent()
    object ToggleMarkPin : NotesEvent()
    object ToggleMenuMore : NotesEvent()
    object OnNoteDeleted : NotesEvent()
}
