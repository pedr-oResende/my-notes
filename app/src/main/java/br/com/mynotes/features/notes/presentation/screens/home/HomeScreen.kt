package br.com.mynotes.features.notes.presentation.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.R
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.compose.components.NotesList
import br.com.mynotes.features.notes.presentation.compose.navigation.Screens
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBar
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.presentation.util.NotesEvent
import br.com.mynotes.ui.theme.MyNotesTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    MyNotesTheme {
        val notesUI = viewModel.notesUI.value
        val scope = rememberCoroutineScope()
        Scaffold(
            topBar = {
                AnimatedVisibility(
                    visible = notesUI.isInSelectedMode,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
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
                                        imageVector = if (notesUI.isPinFilled) {
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
                                        expanded = notesUI.showMenuMore,
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
                }
                AnimatedVisibility(
                    visible = !notesUI.isInSelectedMode,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    TopBar(
                        actions = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TopBarIcon(
                                    onClick = {
                                        scope.launch {
                                            scaffoldState.drawerState.open()
                                        }
                                    },
                                    imageVector = Icons.Filled.Menu
                                )
                                Row {
                                    TopBarIcon(
                                        onClick = {
                                            viewModel.onEvent(NotesEvent.ToggleListView)
                                        },
                                        imageVector = if (notesUI.isInGridMode)
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
                            }
                        }
                    )
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
            ) {
                val notes = notesUI.notes
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
                                    viewModel.onEvent(NotesEvent.RestoreNotes)
                                }
                            }
                        }
                    }
                }
                val fixedNotes = notes.filter { it.isFixed }
                val otherNotes = notes.filter { !it.isFixed }
                val onItemClick: (Note) -> Unit = { note ->
                    if (notesUI.isInSelectedMode)
                        viewModel.onEvent(NotesEvent.SelectNote(note))
                    else
                        viewModel.goToDetail(
                            navHostController = navHostController,
                            note = note
                        )
                }
                val onItemLongClick: (Note) -> Unit = { note ->
                    viewModel.onItemLongClick(note)
                }
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
                } else {
                    NotesList(
                        isInGridMode = notesUI.isInGridMode,
                        notes = notes,
                        onItemClick = onItemClick,
                        onItemLongClick = onItemLongClick
                    )
                }
            }
        }
    }
}