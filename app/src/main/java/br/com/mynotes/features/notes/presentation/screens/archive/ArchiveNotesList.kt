package br.com.mynotes.features.notes.presentation.screens.archive

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import br.com.mynotes.commom.compose.components.NotesList
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.screens.main.MainViewModel
import br.com.mynotes.features.notes.presentation.screens.main.NotesEvents
import br.com.mynotes.features.notes.presentation.util.MainUIEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ArchiveListScreen(
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
    NotesList(
        isInGridMode = notesUI.isInGridMode,
        notes = notes,
        onItemClick = onItemClick,
        onItemLongClick = onItemLongClick
    )
}