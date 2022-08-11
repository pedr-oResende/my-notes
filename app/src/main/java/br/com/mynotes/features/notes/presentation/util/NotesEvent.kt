package br.com.mynotes.features.notes.presentation.util

import br.com.mynotes.features.notes.domain.model.Note

sealed class NotesEvent {
    data class DeleteNote(val note: Note): NotesEvent()
    data class ArchiveNote(val note: Note): NotesEvent()
    data class MarkNote(val note: Note): NotesEvent()
    data class SelectNote(val noteId: Int?): NotesEvent()
    data class AddNote(val note: Note): NotesEvent()
    object ToggleListView: NotesEvent()
    object ToggleCloseSelection: NotesEvent()
}
