package br.com.mynotes.features.notes.presentation.screens.home

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.material.icons.rounded.PushPin
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.R
import br.com.mynotes.commom.compose.navigation.Screens
import br.com.mynotes.commom.compose.widgets.CustomSwipeRefresh
import br.com.mynotes.commom.compose.widgets.TopBar
import br.com.mynotes.commom.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.domain.model.notes
import br.com.mynotes.features.notes.presentation.model.StateUI
import br.com.mynotes.features.notes.presentation.screens.home.components.GridNotesList
import br.com.mynotes.features.notes.presentation.screens.home.components.LinearNotesList
import br.com.mynotes.features.notes.presentation.util.HomeEvent
import br.com.mynotes.features.notes.presentation.util.NotesEvent
import br.com.mynotes.ui.theme.MyNotesTheme
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    viewModel: HomeViewModel = hiltViewModel(),
    homeEvent: HomeEvent?
) {
    MyNotesTheme {
        val state = viewModel.state.value
        val scaffoldState = rememberScaffoldState()
        LaunchedEffect(key1 = true) {
            if (homeEvent != null) {
                viewModel.resolveHomeEvent(homeEvent)
            }
        }
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
                                        viewModel.onEvent(NotesEvent.ToggleCloseSelection(cleanList = true))
                                    },
                                    imageVector = Icons.Default.Close
                                )
                                Row {
                                    TopBarIcon(
                                        onClick = {
                                            viewModel.onEvent(NotesEvent.ToggleMarkPin)
                                        },
                                        imageVector = if (state.togglePin)
                                            Icons.Rounded.PushPin
                                        else
                                            Icons.Outlined.PushPin
                                    )
                                    TopBarIcon(
                                        onClick = { },
                                        imageVector = Icons.Default.MoreVert
                                    )
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
                val notesListUI = viewModel.notesList.collectAsState(StateUI.Idle()).value
                val deleteNoteResult =
                    viewModel.deleteNoteChannel.collectAsState(StateUI.Idle()).value
                val editNoteResult = viewModel.editNoteChannel.collectAsState(StateUI.Idle()).value

                when (deleteNoteResult) {
                    is StateUI.Error -> {
                        LaunchedEffect(key1 = true) {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Ocorreu um Erro inesperado!"
                            )
                        }
                    }
                    is StateUI.Processed -> {
                        viewModel.onEvent(NotesEvent.OnNoteDeleted)
                    }
                    is StateUI.Processing -> Unit
                    is StateUI.Idle -> Unit
                }
                LaunchedEffect(key1 = state.aNoteHasBeenDeleted) {
                    viewModel.onEvent(NotesEvent.ToggleCloseSelection(cleanList = false))
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = "Nota removida com sucesso!"
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                            viewModel.onEvent(NotesEvent.RestoreNotes)
                        }
                        SnackbarResult.Dismissed -> {
                            viewModel.cleanSelectedList()
                        }
                    }
                }
                CustomSwipeRefresh(
                    swipeRefreshState = rememberSwipeRefreshState(
                        isRefreshing = notesListUI.loading() ||
                                deleteNoteResult.loading() ||
                                editNoteResult.loading()
                    ),
                    onRefresh = { viewModel.refresh() }
                ) {
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
}