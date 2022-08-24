package br.com.mynotes.commom.compose.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.screens.main.components.NoteItem

@Composable
fun LinearNotesList(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onItemClick: (Note) -> Unit,
    onItemLongClick: (Note) -> Unit
) {
    LazyColumn(modifier = modifier) {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        items(notes) { note ->
            NoteItem(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                note = note,
                onClick = {
                    onItemClick(note)
                },
                onLongClick = {
                    onItemLongClick(note)
                }
            )
        }
        item { Spacer(modifier = Modifier.height(8.dp)) }
    }
}