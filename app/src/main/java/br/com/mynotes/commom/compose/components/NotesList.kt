package br.com.mynotes.commom.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.mynotes.features.notes.domain.model.Note

@Composable
fun NotesList(
    modifier: Modifier = Modifier,
    isInGridMode: Boolean,
    notes: List<Note>,
    onItemClick: (Note) -> Unit,
    onItemLongClick: (Note) -> Unit
) {
    if (isInGridMode) {
        GridNotesList(
            modifier = modifier,
            notes = notes,
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick
        )
    } else {
        LinearNotesList(
            modifier = modifier,
            notes = notes,
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick
        )
    }
}