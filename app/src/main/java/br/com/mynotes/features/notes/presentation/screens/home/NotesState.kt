package br.com.mynotes.features.notes.presentation.screens.home

import br.com.mynotes.features.notes.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList(),
    val isInGridMode: Boolean = true,
    val isInSelectedMode: Boolean = false,
    val togglePin: Boolean = false,
    val aNoteHasBeenDeleted: Boolean = false,
    val isInFixedMode: Boolean = false
)
