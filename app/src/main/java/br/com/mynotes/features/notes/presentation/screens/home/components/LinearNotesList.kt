package br.com.mynotes.features.notes.presentation.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.mynotes.features.notes.domain.model.Note

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LinearNotesList(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    LazyColumn(modifier = modifier) {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        items(notes) { note ->
            NoteItem(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .combinedClickable(
                        onClick = onClick,
                        onLongClick = onLongClick
                    ),
                note = note
            )
        }
        item { Spacer(modifier = Modifier.height(8.dp)) }
    }
}