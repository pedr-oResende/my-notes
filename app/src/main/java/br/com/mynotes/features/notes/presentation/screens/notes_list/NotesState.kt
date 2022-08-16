package br.com.mynotes.features.notes.presentation.screens.notes_list

import br.com.mynotes.features.notes.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList(),
    val selectedNotes: List<Note> = emptyList(),
    val isInGridMode: Boolean = true,
    val isInSelectedMode: Boolean = false,
    val aNoteHasBeenDeleted: Boolean = false,
    val showMenuMore: Boolean = false,
    val isPinFilled: Boolean = true
)
