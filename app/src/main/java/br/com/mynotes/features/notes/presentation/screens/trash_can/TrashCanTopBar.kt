package br.com.mynotes.features.notes.presentation.screens.trash_can

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.RestoreFromTrash
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBar
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.presentation.screens.main.MainViewModel
import br.com.mynotes.features.notes.presentation.screens.main.NotesUI
import br.com.mynotes.features.notes.presentation.screens.main.ScreenState
import br.com.mynotes.features.notes.presentation.util.MainUIEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TrashCanTopBar(
    notesUI: NotesUI,
    viewModel: MainViewModel,
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
                            onClick = { viewModel.onEvent(MainUIEvents.ToggleMenuMore) },
                            imageVector = Icons.Filled.MoreVert
                        )
                        DropdownMenu(
                            expanded = notesUI.showMenuMore,
                            onDismissRequest = { viewModel.onEvent(MainUIEvents.ToggleMenuMore) }) {
                            DropdownMenuItem(onClick = { viewModel.onEvent(MainUIEvents.ClearTrashCan) }) {
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
                                onClick = { viewModel.onEvent(MainUIEvents.ToggleCloseSelection) },
                                imageVector = Icons.Filled.Close
                            )
                            Text(
                                text = viewModel.selectedNotesSize().toString(),
                                style = MaterialTheme.typography.h5
                            )
                        }
                        Row {
                            TopBarIcon(
                                onClick = { viewModel.onEvent(MainUIEvents.RestoreFromTrashCan) },
                                imageVector = Icons.Outlined.RestoreFromTrash
                            )
                            TopBarIcon(
                                onClick = { viewModel.onEvent(MainUIEvents.ToggleMenuMore) },
                                imageVector = Icons.Filled.MoreVert
                            )
                            DropdownMenu(
                                expanded = notesUI.showMenuMore,
                                onDismissRequest = { viewModel.onEvent(MainUIEvents.ToggleMenuMore) }) {
                                DropdownMenuItem(onClick = {
                                    viewModel.onEvent(MainUIEvents.DeleteNotes)
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