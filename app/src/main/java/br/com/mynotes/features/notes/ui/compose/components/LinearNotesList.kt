package br.com.mynotes.features.notes.ui.compose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.ui.screens.main.components.NoteItem

@OptIn(ExperimentalFoundationApi::class)
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
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clip(MaterialTheme.shapes.large)
                    .combinedClickable(
                        onClick = {
                            onItemClick(note)
                        },
                        onLongClick = {
                            onItemLongClick(note)
                        }
                    )
                ,
                note = note
            )
        }
        item { Spacer(modifier = Modifier.height(8.dp)) }
    }
}