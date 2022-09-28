package br.com.mynotes.features.notes.ui.screens.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.mynotes.R
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.ui.compose.components.NotesList
import br.com.mynotes.features.notes.ui.screens.home.HomeViewModel

@Composable
fun HomeList(
    viewModel: HomeViewModel,
    onItemClick: (Note) -> Unit,
    onItemLongClick: (Note) -> Unit
) {
    val notesUI = viewModel.notesUI.value
    val notes = viewModel.getNotesListFilteredByText()
    val fixedNotes = notes.filter { it.isFixed }
    val otherNotes = notes.filter { !it.isFixed }
    if (fixedNotes.isNotEmpty()) {
        Text(
            text = stringResource(R.string.notes_list_fixed_label),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )
        NotesList(
            isInGridMode = notesUI.isInGridMode,
            notes = fixedNotes,
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick
        )
        if (otherNotes.isNotEmpty()) {
            Text(
                text = stringResource(R.string.notes_list_others_label),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
            NotesList(
                isInGridMode = notesUI.isInGridMode,
                notes = otherNotes,
                onItemClick = onItemClick,
                onItemLongClick = onItemLongClick
            )
        }
    } else {
        NotesList(
            isInGridMode = notesUI.isInGridMode,
            notes = notes,
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick
        )
    }
}