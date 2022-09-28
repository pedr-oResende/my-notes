package br.com.mynotes.features.notes.ui.screens.archive

import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.ui.compose.components.NotesList
import br.com.mynotes.features.notes.ui.screens.main.MainViewModel
import br.com.mynotes.features.notes.ui.screens.main.state.MainUIEvents
import br.com.mynotes.features.notes.ui.screens.main.state.NotesActions
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ArchiveListScreen(
    viewModel: MainViewModel,
    onItemClick: (Note) -> Unit,
    onItemLongClick: (Note) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val notesUI = viewModel.notesUI.value
    val notes = viewModel.getNotesListFiltered()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is NotesActions.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is NotesActions.ShowUndoSnackBar -> {
                    val result = snackbarHostState.showSnackbar(
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