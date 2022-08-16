package br.com.mynotes.features.notes.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.R
import br.com.mynotes.features.notes.presentation.compose.navigation.Screens
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBar
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.presentation.screens.home.components.GridNotesList
import br.com.mynotes.features.notes.presentation.screens.home.components.LinearNotesList
import br.com.mynotes.features.notes.presentation.util.NotesEvent
import br.com.mynotes.ui.theme.MyNotesTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    MyNotesTheme {
        val state = viewModel.state.value
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            topBar = {
                if (state.isInSelectedMode) {
                    TopBar(
                        actions = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TopBarIcon(
                                    onClick = {
                                        viewModel.onEvent(NotesEvent.ToggleCloseSelection)
                                    },
                                    imageVector = Icons.Filled.Close
                                )
                                Row {
                                    TopBarIcon(
                                        onClick = {
                                            viewModel.onEvent(NotesEvent.ToggleMarkPin)
                                        },
                                        imageVector = if (state.isPinFilled) {
                                            Icons.Filled.PushPin
                                        } else {
                                            Icons.Outlined.PushPin
                                        }
                                    )
                                    TopBarIcon(
                                        onClick = {
                                            viewModel.onEvent(NotesEvent.ToggleMenuMore)
                                        },
                                        imageVector = Icons.Filled.MoreVert
                                    )
                                    DropdownMenu(
                                        expanded = state.showMenuMore,
                                        onDismissRequest = { viewModel.onEvent(NotesEvent.ToggleMenuMore) }) {
                                        DropdownMenuItem(onClick = {
                                            viewModel.onEvent(NotesEvent.ArchiveNote)
                                        }) {
                                            Text(
                                                text = stringResource(R.string.dropdown_label_archive),
                                                style = MaterialTheme.typography.body1
                                            )
                                        }
                                        DropdownMenuItem(onClick = {
                                            viewModel.onEvent(NotesEvent.DeleteNote)
                                        }) {
                                            Text(
                                                text = stringResource(R.string.dropdown_label_delete),
                                                style = MaterialTheme.typography.body1
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    )
                } else {
                    TopBar(
                        title = stringResource(id = R.string.app_name),
                        actions = {
                            TopBarIcon(
                                onClick = {
                                    viewModel.onEvent(NotesEvent.ToggleListView)
                                },
                                imageVector = if (state.isInGridMode)
                                    Icons.Outlined.ViewAgenda
                                else
                                    Icons.Outlined.GridView
                            )
                            TopBarIcon(
                                onClick = {
                                    Screens.NoteDetail.navigate(navHostController)
                                },
                                imageVector = Icons.Default.Edit
                            )
                        }
                    )
                }
            },
            scaffoldState = scaffoldState
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
            ) {
                val notes = state.notes
                LaunchedEffect(key1 = true) {
                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is UIEvents.ShowSnackBar -> {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = event.message
                                )
                            }
                            is UIEvents.ShowUndoSnackBar -> {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = event.text,
                                    actionLabel = event.label
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NotesEvent.RestoreNotes)
                                }
                            }
                        }
                    }
                }
                if (state.isInGridMode) {
                    GridNotesList(
                        notes = notes,
                        viewModel = viewModel,
                        navHostController = navHostController
                    )
                } else {
                    LinearNotesList(
                        notes = notes,
                        viewModel = viewModel,
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}