package br.com.mynotes.features.notes.presentation.util


sealed class NoteDetailEvent {
    data class TitleChanged(val title: String): NoteDetailEvent()
    data class ContentChanged(val content: String): NoteDetailEvent()
    object ToggleMarkPin : NoteDetailEvent()
    object SaveNote: NoteDetailEvent()
    object DeleteNote: NoteDetailEvent()
    object ArchiveNote: NoteDetailEvent()
}
