package br.com.mynotes.features.notes.ui.screens.home

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.ui.screens.home.components.HomeList
import br.com.mynotes.features.notes.ui.screens.home.ui.HomeEvents
import br.com.mynotes.features.notes.ui.screens.main.ui.NoteListState
import br.com.mynotes.features.notes.ui.screens.main.ui.SnackBarEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeNotesScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    noteListState: NoteListState
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
                        viewModel.onEvent(HomeEvents.RestoreNotes)
                    }
                }
            }
        }
    }
    HomeList(
        viewModel = viewModel,
        navHostController = navHostController,
        noteListState = noteListState
    )
}