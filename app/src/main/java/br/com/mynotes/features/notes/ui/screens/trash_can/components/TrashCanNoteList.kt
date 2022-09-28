package br.com.mynotes.features.notes.ui.screens.trash_can.components

import androidx.compose.runtime.Composable
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.ui.compose.components.NotesList

@Composable
fun TrashCanNoteList(
    notes: List<Note>,
    onItemClick: (Note) -> Unit,
    onItemLongClick: (Note) -> Unit
) {
    NotesList(
        isInGridMode = true,
        notes = notes,
        onItemClick = onItemClick,
        onItemLongClick = onItemLongClick
    )
}