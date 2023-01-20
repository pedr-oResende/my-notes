package br.com.mynotes.features.notes.ui.compose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.ui.compose.animations.CustomSwipeToDismiss

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LinearNotesList(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onItemClick: (Note) -> Unit,
    onItemLongClick: (Note) -> Unit,
    onDismiss: ((Note) -> Unit)?
) {
    LazyColumn(modifier = modifier.padding(top = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(notes) { note ->
            CustomSwipeToDismiss(
                onDismiss = { onDismiss?.invoke(note) },
                enabled = onDismiss != null
            ) { alpha ->
                NoteItem(
                    modifier = Modifier
                        .alpha(alpha)
                        .padding(horizontal = 16.dp)
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