package br.com.mynotes.features.notes.presentation.util

import br.com.mynotes.features.notes.domain.model.Note

sealed class NoteDetailEvent {
    data class DeleteNote(val note: Note?): NoteDetailEvent()
    data class ArchiveNote(val note: Note?): NoteDetailEvent()
    data class SaveNote(val note: Note?): NoteDetailEvent()
    data class TitleChanged(val title: String): NoteDetailEvent()
    data class ContentChanged(val content: String): NoteDetailEvent()
    object ToggleMarkPin : NoteDetailEvent()
}
