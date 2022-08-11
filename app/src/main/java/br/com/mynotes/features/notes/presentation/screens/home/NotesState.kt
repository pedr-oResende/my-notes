package br.com.mynotes.features.notes.presentation.screens.home

import br.com.mynotes.features.notes.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList(),
    val isInGridMode: Boolean = true
)
