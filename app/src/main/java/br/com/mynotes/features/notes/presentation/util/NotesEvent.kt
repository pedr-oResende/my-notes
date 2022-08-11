package br.com.mynotes.features.notes.presentation.util

import br.com.mynotes.features.notes.domain.model.Note

sealed class NotesEvent {
    data class DeleteNote(val note: Note): NotesEvent()
    object ToggleListView: NotesEvent()
}
