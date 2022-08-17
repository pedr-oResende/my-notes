package br.com.mynotes.features.notes.presentation.compose.components

import androidx.compose.runtime.Composable
import br.com.mynotes.features.notes.domain.model.Note

@Composable
fun NotesList(
    isInGridMode: Boolean,
    notes: List<Note>,
    onItemClick: (Note) -> Unit,
    onItemLongClick: (Note) -> Unit
) {
    if (isInGridMode) {
        GridNotesList(
            notes = notes,
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick
        )
    } else {
        LinearNotesList(
            notes = notes,
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick
        )
    }
}