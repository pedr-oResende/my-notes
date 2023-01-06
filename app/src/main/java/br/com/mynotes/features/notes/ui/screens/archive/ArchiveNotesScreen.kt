package br.com.mynotes.features.notes.ui.screens.archive

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.ui.screens.archive.components.ArchiveNotesList
import br.com.mynotes.features.notes.ui.screens.archive.ui.ArchiveEvents
import br.com.mynotes.features.notes.ui.screens.main.ui.SnackBarEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ArchiveNotesScreen(
    navHostController: NavHostController,
    viewModel: ArchiveViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    isInGridMode: Boolean
) {
    LaunchedEffect(key1 = true) {
        val emptyNoteMessage = viewModel.getSnackBarMessage()
        if (emptyNoteMessage.isNotBlank()) {
            snackbarHostState.showSnackbar(
                message = emptyNoteMessage
            )
        }
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SnackBarEvents.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is SnackBarEvents.ShowUndoSnackBar -> {
                    val result = snackbarHostState.showSnackbar(
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
        isInGridMode = isInGridMode
    )
}
