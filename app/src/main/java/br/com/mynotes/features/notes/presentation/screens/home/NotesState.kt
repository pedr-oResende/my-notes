package br.com.mynotes.features.notes.presentation.screens.home

import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.util.NoteOrder
import br.com.mynotes.features.notes.presentation.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val isInGridMode: Boolean = true
)
