package br.com.mynotes.features.notes.presentation.screens.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.RestoreFromTrash
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.mynotes.R
import br.com.mynotes.features.notes.presentation.compose.widgets.CustomEditText
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBar
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.presentation.screens.home.HomeViewModel
import br.com.mynotes.features.notes.presentation.screens.home.NotesUI
import br.com.mynotes.features.notes.presentation.screens.home.ScreenState
import br.com.mynotes.features.notes.presentation.util.HomeUIEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NotesListTopBar(
    viewModel: HomeViewModel,
    scaffoldState: ScaffoldState
) {
    val notesUI = viewModel.notesUI.value
    val scope = rememberCoroutineScope()
    HomeTopBar(notesUI = notesUI, viewModel = viewModel)
    ArchiveTopBar(notesUI = notesUI, viewModel = viewModel)
    TrashCanTopBar(
        notesUI = notesUI,
        viewModel = viewModel,
        scope = scope,
        scaffoldState = scaffoldState
    )
    AnimatedVisibility(
        visible = !notesUI.isInSelectedMode && notesUI.screenState != ScreenState.TrashCanScreen,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        CustomEditText(
            modifier = Modifier
                .padding(top = 18.dp, start = 16.dp, end = 16.dp)
                .clip(RoundedCornerShape(50)),
            placeholder = stringResource(R.string.search_note_placeholder),
            value = notesUI.searchNotesText,
            onValueChange = { newText ->
                viewModel.onEvent(HomeUIEvents.SearchTextChanged(newText))
            },
            leadingIcon = {
                TopBarIcon(
                    onClick = {
                        scope.launch { scaffoldState.drawerState.open() }
                    },
                    imageVector = Icons.Filled.Menu
                )
            },
            trailingIcon = {
                Row {
                    TopBarIcon(
                        onClick = { viewModel.onEvent(HomeUIEvents.ToggleListView) },
                        imageVector = if (notesUI.isInGridMode)
                            Icons.Outlined.ViewAgenda
                        else
                            Icons.Outlined.GridView
                    )
                }
            }
        )
    }
}

@Composable
fun HomeTopBar(notesUI: NotesUI, viewModel: HomeViewModel) {
    AnimatedVisibility(
        visible = notesUI.isInSelectedMode && notesUI.screenState == ScreenState.HomeScreen,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column {
            TopBar(
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TopBarIcon(
                                onClick = { viewModel.onEvent(HomeUIEvents.ToggleCloseSelection) },
                                imageVector = Icons.Filled.Close
                            )
                            Text(
                                text = viewModel.selectedNotesSize().toString(),
                                style = MaterialTheme.typography.h5
                            )
                        }
                        Row {
                            TopBarIcon(
                                onClick = { viewModel.onEvent(HomeUIEvents.ToggleMarkPin) },
                                imageVector = if (notesUI.isPinFilled)
                                    Icons.Filled.PushPin
                                 else
                                    Icons.Outlined.PushPin
                            )
                            TopBarIcon(
                                onClick = {
                                    viewModel.onEvent(HomeUIEvents.ToggleMenuMore)
                                },
                                imageVector = Icons.Filled.MoreVert
                            )
                            DropdownMenu(
                                expanded = notesUI.showMenuMore,
                                onDismissRequest = { viewModel.onEvent(HomeUIEvents.ToggleMenuMore) }) {
                                DropdownMenuItem(onClick = { viewModel.onEvent(HomeUIEvents.ArchiveNote(archive = true)) }) {
                                    Text(
                                        text = stringResource(R.string.label_archive),
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                                DropdownMenuItem(onClick = { viewModel.onEvent(HomeUIEvents.MoveNoteToTrashCan) }) {
                                    Text(
                                        text = stringResource(R.string.label_delete),
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                            }
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.3.dp))
        }
    }
}

@Composable
fun ArchiveTopBar(
    notesUI: NotesUI,
    viewModel: HomeViewModel
) {
    AnimatedVisibility(
        visible = notesUI.isInSelectedMode && notesUI.screenState == ScreenState.ArchiveScreen,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column {
            TopBar(
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TopBarIcon(
                                onClick = { viewModel.onEvent(HomeUIEvents.ToggleCloseSelection) },
                                imageVector = Icons.Filled.Close
                            )
                            Text(
                                text = viewModel.selectedNotesSize().toString(),
                                style = MaterialTheme.typography.h5
                            )
                        }
                        Row {
                            TopBarIcon(
                                onClick = { viewModel.onEvent(HomeUIEvents.ToggleMarkPin) },
                                imageVector = if (notesUI.isPinFilled) {
                                    Icons.Filled.PushPin
                                } else {
                                    Icons.Outlined.PushPin
                                }
                            )
                            TopBarIcon(
                                onClick = { viewModel.onEvent(HomeUIEvents.ToggleMenuMore) },
                                imageVector = Icons.Filled.MoreVert
                            )
                            DropdownMenu(
                                expanded = notesUI.showMenuMore,
                                onDismissRequest = { viewModel.onEvent(HomeUIEvents.ToggleMenuMore) }) {
                                DropdownMenuItem(onClick = { viewModel.onEvent(HomeUIEvents.ArchiveNote(false)) }) {
                                    Text(
                                        text = stringResource(R.string.label_unarchive),
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                                DropdownMenuItem(onClick = { viewModel.onEvent(HomeUIEvents.MoveNoteToTrashCan) }) {
                                    Text(
                                        text = stringResource(R.string.label_delete),
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                            }
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.3.dp))
        }
    }
}

@Composable
fun TrashCanTopBar(
    notesUI: NotesUI,
    viewModel: HomeViewModel,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    AnimatedVisibility(
        visible = !notesUI.isInSelectedMode && notesUI.screenState == ScreenState.TrashCanScreen,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column {
            TopBar(
                title = "Lixeira",
                navigationIcon = {
                    TopBarIcon(
                        onClick = {
                            scope.launch { scaffoldState.drawerState.open() }
                        },
                        imageVector = Icons.Filled.Menu
                    )
                },
                actions = {
                    Row {
                        TopBarIcon(
                            onClick = { viewModel.onEvent(HomeUIEvents.ToggleMenuMore) },
                            imageVector = Icons.Filled.MoreVert
                        )
                        DropdownMenu(
                            expanded = notesUI.showMenuMore,
                            onDismissRequest = { viewModel.onEvent(HomeUIEvents.ToggleMenuMore) }) {
                            DropdownMenuItem(onClick = { viewModel.onEvent(HomeUIEvents.ClearTrashCan) }) {
                                Text(
                                    text = "Esvaziar lixeira",
                                    style = MaterialTheme.typography.body1
                                )
                            }
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.3.dp))
        }
    }
    AnimatedVisibility(
        visible = notesUI.isInSelectedMode && notesUI.screenState == ScreenState.TrashCanScreen,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column {
            TopBar(
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TopBarIcon(
                                onClick = { viewModel.onEvent(HomeUIEvents.ToggleCloseSelection) },
                                imageVector = Icons.Filled.Close
                            )
                            Text(
                                text = viewModel.selectedNotesSize().toString(),
                                style = MaterialTheme.typography.h5
                            )
                        }
                        Row {
                            TopBarIcon(
                                onClick = { viewModel.onEvent(HomeUIEvents.RestoreFromTrashCan) },
                                imageVector = Icons.Outlined.RestoreFromTrash
                            )
                            TopBarIcon(
                                onClick = { viewModel.onEvent(HomeUIEvents.ToggleMenuMore) },
                                imageVector = Icons.Filled.MoreVert
                            )
                            DropdownMenu(
                                expanded = notesUI.showMenuMore,
                                onDismissRequest = { viewModel.onEvent(HomeUIEvents.ToggleMenuMore) }) {
                                DropdownMenuItem(onClick = {
                                    viewModel.onEvent(HomeUIEvents.DeleteNotes)
                                }) {
                                    Text(
                                        text = "Excluir definitivamente",
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                            }
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.3.dp))
        }
    }
}