package br.com.mynotes.features.notes.ui.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.ui.screens.main.ui.NoteListState

@Composable
fun NotesList(
    modifier: Modifier = Modifier,
    noteListState: NoteListState,
    notes: List<Note>,
    onItemClick: (Note) -> Unit,
    onItemLongClick: (Note) -> Unit,
    onSwipe: ((Note) -> Unit)? = null
) {
    when (noteListState) {
        NoteListState.Grid -> GridNotesList(
            modifier = modifier,
            notes = notes,
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick,
            onDismiss = onSwipe
        )
        NoteListState.Linear -> LinearNotesList(
            modifier = modifier,
            notes = notes,
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick,
            onDismiss = onSwipe
        )
    }
}