package br.com.mynotes.features.notes.presentation.screens.note_detail

import br.com.mynotes.features.notes.domain.model.Note

data class NoteDetailUI(
    val note: Note? = null,
    val title: String = "",
    val content: String = "",
    val isPinMarked: Boolean = false
)
