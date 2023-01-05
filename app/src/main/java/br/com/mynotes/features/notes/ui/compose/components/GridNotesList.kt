package br.com.mynotes.features.notes.ui.compose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import br.com.mynotes.features.notes.domain.model.Note

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridNotesList(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onItemClick: (Note) -> Unit,
    onItemLongClick: (Note) -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier.padding(8.dp),
        columns = StaggeredGridCells.Fixed(2)
    ) {
        items(notes) { note ->
            NoteItem(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .clip(MaterialTheme.shapes.large)
                    .combinedClickable(
                        onClick = {
                            onItemClick(note)
                        },
                        onLongClick = {
                            onItemLongClick(note)
                        }
                    ),
                note = note
            )
        }
    }
}