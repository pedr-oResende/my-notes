package br.com.mynotes.features.notes.presentation.compose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.compose.animations.CustomSwipeToDismiss

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridNotesList(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onItemClick: (Note) -> Unit,
    onItemLongClick: (Note) -> Unit,
    onDismiss: ((Note) -> Unit)?
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier.padding(8.dp),
        columns = StaggeredGridCells.Fixed(2)
    ) {
        items(notes) { note ->
            CustomSwipeToDismiss(
                onDismiss = { onDismiss?.invoke(note) },
                enabled = onDismiss != null
            ) { alpha ->
                NoteItem(
                    modifier = Modifier
                        .alpha(alpha)
                        .padding(all = 4.dp)
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
}