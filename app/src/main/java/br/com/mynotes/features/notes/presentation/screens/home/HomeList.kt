package br.com.mynotes.features.notes.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.mynotes.R
import br.com.mynotes.commom.compose.components.NotesList
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.screens.main.MainViewModel
import br.com.mynotes.features.notes.presentation.screens.main.NotesEvents
import br.com.mynotes.features.notes.presentation.util.MainUIEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeList(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState,
    onItemClick: (Note) -> Unit,
    onItemLongClick: (Note) -> Unit
) {
    val notesUI = viewModel.notesUI.value
    val notes = viewModel.getNotesListFiltered()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is NotesEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is NotesEvents.ShowUndoSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.text,
                        actionLabel = event.label
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(MainUIEvents.RestoreNotes)
                    }
                }
            }
        }
    }
    val fixedNotes = notes.filter { it.isFixed }
    val otherNotes = notes.filter { !it.isFixed }
    if (fixedNotes.isNotEmpty()) {
        Text(
            text = stringResource(R.string.notes_list_fixed_label),
            style = MaterialTheme.typography.body2,
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
                style = MaterialTheme.typography.body2,
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