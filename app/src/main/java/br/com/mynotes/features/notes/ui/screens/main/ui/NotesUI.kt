package br.com.mynotes.features.notes.ui.screens.main.ui

import br.com.mynotes.features.notes.domain.model.Note

data class NotesUI(
    val notes: List<Note> = emptyList(),
    val searchNotesText: String = "",
    val isInSelectedMode: Boolean = false,
    val showMenuMore: Boolean = false,
    val isPinFilled: Boolean = true
)
