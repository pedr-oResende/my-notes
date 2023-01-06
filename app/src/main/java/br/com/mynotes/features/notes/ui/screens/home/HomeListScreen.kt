package br.com.mynotes.features.notes.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.R
import br.com.mynotes.features.notes.ui.compose.components.NotesList
import br.com.mynotes.features.notes.ui.compose.navigation.Screens
import br.com.mynotes.features.notes.ui.screens.home.components.HomeTopBar
import br.com.mynotes.features.notes.ui.screens.home.ui.HomeEvents
import br.com.mynotes.features.notes.ui.screens.main.ui.NotesActions
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeListScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    drawerState: DrawerState
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
                        viewModel.onEvent(HomeEvents.RestoreNotes)
                    }
                }
            }
        }
    }
    Scaffold(
        topBar = {
            HomeTopBar(
                viewModel = viewModel,
                drawerState = drawerState
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(Screens.NoteDetail.route)
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            HomeList(
                viewModel = viewModel,
                navHostController = navHostController
            )
        }
    }
}

@Composable
fun HomeList(
    viewModel: HomeViewModel,
    navHostController: NavHostController
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
            onItemClick = { note ->
                viewModel.onItemClick(note, navHostController)
            },
            onItemLongClick = { note ->
                viewModel.onItemLongClick(note)
            }
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
                onItemClick = { note ->
                    viewModel.onItemClick(note, navHostController)
                },
                onItemLongClick = { note ->
                    viewModel.onItemLongClick(note)
                }
            )
        }
    } else {
        NotesList(
            isInGridMode = notesUI.isInGridMode,
            notes = notes,
            onItemClick = { note ->
                viewModel.onItemClick(note, navHostController)
            },
            onItemLongClick = { note ->
                viewModel.onItemLongClick(note)
            }
        )
    }
}