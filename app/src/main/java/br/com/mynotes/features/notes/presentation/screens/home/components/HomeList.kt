package br.com.mynotes.features.notes.presentation.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.mynotes.R
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.compose.components.NotesList
import br.com.mynotes.features.notes.presentation.screens.home.HomeViewModel
import br.com.mynotes.features.notes.presentation.screens.main.ui.NoteListState

@Composable
fun HomeList(
    viewModel: HomeViewModel,
    navHostController: NavHostController,
    noteListState: NoteListState
) {
    val notes = viewModel.getNotesListFilteredByText()
    val fixedNotes = notes.filter { it.isFixed }
    val otherNotes = notes.filter { !it.isFixed }
    val onItemClick: (Note) -> Unit = { note ->
        viewModel.onItemClick(note, navHostController)
    }
    val onItemLongClick: (Note) -> Unit = { note ->
        viewModel.onItemLongClick(note)
    }
    val archiveOnSwipe: (Note) -> Unit = { note ->
        viewModel.archiveOnSwipe(note)
    }
    if (fixedNotes.isNotEmpty()) {
        Column {
            Text(
                text = stringResource(R.string.notes_list_fixed_label),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            NotesList(
                noteListState = noteListState,
                notes = fixedNotes,
                onItemClick = onItemClick,
                onItemLongClick = onItemLongClick,
                onSwipe = archiveOnSwipe
            )
            if (otherNotes.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.notes_list_others_label),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp)
                )
                NotesList(
                    noteListState = noteListState,
                    notes = otherNotes,
                    onItemClick = onItemClick,
                    onItemLongClick = onItemLongClick,
                    onSwipe = archiveOnSwipe
                )
            }
        }
    } else {
        NotesList(
            noteListState = noteListState,
            notes = notes,
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick,
            onSwipe = archiveOnSwipe
        )
    }
}