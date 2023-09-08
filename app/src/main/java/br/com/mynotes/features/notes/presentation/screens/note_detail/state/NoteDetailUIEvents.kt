package br.com.mynotes.features.notes.presentation.screens.note_detail.state


sealed class NoteDetailUIEvents {
    data class TitleChanged(val title: String): NoteDetailUIEvents()
    data class ContentChanged(val content: String): NoteDetailUIEvents()
    data object ToggleMarkPin : NoteDetailUIEvents()
    data object SaveNote: NoteDetailUIEvents()
    data object DeleteNote: NoteDetailUIEvents()
    data object RestoreNote: NoteDetailUIEvents()
    data object ArchiveNote: NoteDetailUIEvents()
    data object TryToEditDeletedNote: NoteDetailUIEvents()
}
