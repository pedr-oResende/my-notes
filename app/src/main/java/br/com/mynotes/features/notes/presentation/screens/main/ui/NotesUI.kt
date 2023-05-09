package br.com.mynotes.features.notes.presentation.screens.main.ui

import br.com.mynotes.features.notes.domain.model.Note

data class NotesUI(
    val notes: List<Note> = emptyList(),
    val searchNotesText: String = "",
    val isInSelectedMode: Boolean = false,
    val isPinFilled: Boolean = true
)
