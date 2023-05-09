package br.com.mynotes.features.notes.presentation.screens.archive

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.presentation.screens.archive.components.ArchiveNotesList
import br.com.mynotes.features.notes.presentation.screens.archive.ui.ArchiveEvents
import br.com.mynotes.features.notes.presentation.screens.main.ui.NoteListState
import br.com.mynotes.features.notes.presentation.screens.main.ui.SnackBarEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ArchiveNotesScreen(
    navHostController: NavHostController,
    viewModel: ArchiveViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState,
    noteListState: NoteListState
) {
    LaunchedEffect(key1 = true) {
        val emptyNoteMessage = viewModel.getSnackBarMessage()
        if (emptyNoteMessage.isNotBlank()) {
            snackBarHostState.showSnackbar(
                message = emptyNoteMessage
            )
        }
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SnackBarEvents.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is SnackBarEvents.ShowUndoSnackBar -> {
                    val result = snackBarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.label
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(ArchiveEvents.RestoreNotes)
                    }
                }
            }
        }
    }
    ArchiveNotesList(
        viewModel = viewModel,
        navHostController = navHostController,
        noteListState = noteListState
    )
}
