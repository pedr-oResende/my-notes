package br.com.mynotes.features.notes.ui.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.mynotes.features.notes.domain.model.Note

@Composable
fun NotesList(
    modifier: Modifier = Modifier,
    isInGridMode: Boolean,
    notes: List<Note>,
    onItemClick: (Note) -> Unit,
    onItemLongClick: (Note) -> Unit,
    onSwipe: ((Note) -> Unit)? = null
) {
    if (isInGridMode) {
        GridNotesList(
            modifier = modifier,
            notes = notes,
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick,
            onDismiss = onSwipe
        )
    } else {
        LinearNotesList(
            modifier = modifier,
            notes = notes,
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick,
            onDismiss = onSwipe
        )
    }
}