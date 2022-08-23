package br.com.mynotes.features.notes.presentation.util


sealed class NoteDetailUIEvents {
    data class TitleChanged(val title: String): NoteDetailUIEvents()
    data class ContentChanged(val content: String): NoteDetailUIEvents()
    object ToggleMarkPin : NoteDetailUIEvents()
    object SaveNote: NoteDetailUIEvents()
    object DeleteNote: NoteDetailUIEvents()
    object RestoreNote: NoteDetailUIEvents()
    object ArchiveNote: NoteDetailUIEvents()
    object TryToEditDeletedNote: NoteDetailUIEvents()
}
