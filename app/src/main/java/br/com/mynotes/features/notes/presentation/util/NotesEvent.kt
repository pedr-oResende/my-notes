package br.com.mynotes.features.notes.presentation.util

import br.com.mynotes.features.notes.domain.model.Note

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object ToggleOrderSection: NotesEvent()
    object ToggleListView: NotesEvent()
}
