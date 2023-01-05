package br.com.mynotes.features.notes.ui.screens.archive

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.ui.compose.theme.MyNotesTheme
import br.com.mynotes.features.notes.ui.screens.archive.components.ArchiveNoteList
import br.com.mynotes.features.notes.ui.screens.archive.components.ArchiveTopBar
import br.com.mynotes.features.notes.ui.screens.archive.ui.ArchiveEvents
import br.com.mynotes.features.notes.ui.screens.main.state.NotesActions
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveListScreen(
    navHostController: NavHostController,
    viewModel: ArchiveViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    drawerStateHost: DrawerState
) {
    val notesUI = viewModel.notesUI.value
    LaunchedEffect(key1 = true) {
        val emptyNoteMessage = viewModel.getSnackBarMessage()
        if (emptyNoteMessage.isNotBlank()) {
            snackbarHostState.showSnackbar(
                message = emptyNoteMessage
            )
        }
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
                        viewModel.onEvent(ArchiveEvents.RestoreNotes)
                    }
                }
            }
        }
    }
    MyNotesTheme {
        Scaffold(
            topBar = {
                ArchiveTopBar(
                    notesUI = notesUI,
                    viewModel = viewModel,
                    drawerStateHost = drawerStateHost
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
            ) {
                ArchiveNoteList(
                    viewModel = viewModel,
                    navHostController = navHostController
                )
            }
        }
    }
}