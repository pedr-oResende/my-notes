package br.com.mynotes.features.notes.presentation.screens.home

import br.com.mynotes.features.notes.domain.model.Note

data class NotesUI(
    val notes: List<Note> = emptyList(),
    val searchNotesText: String = "",
    val isInGridMode: Boolean = true,
    val isInSelectedMode: Boolean = false,
    val aNoteHasBeenDeleted: Boolean = false,
    val showMenuMore: Boolean = false,
    val isPinFilled: Boolean = true,
    val screenState: ScreenState = ScreenState.HomeScreen
)
