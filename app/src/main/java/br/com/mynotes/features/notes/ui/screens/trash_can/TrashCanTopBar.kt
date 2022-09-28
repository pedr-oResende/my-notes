package br.com.mynotes.features.notes.ui.screens.trash_can

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.RestoreFromTrash
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.mynotes.features.notes.ui.compose.widgets.TopBar
import br.com.mynotes.features.notes.ui.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.ui.screens.main.MainViewModel
import br.com.mynotes.features.notes.ui.screens.main.state.MainUIEvents
import br.com.mynotes.features.notes.ui.screens.main.state.NotesUI
import br.com.mynotes.features.notes.ui.screens.main.state.ScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashCanTopBar(
    notesUI: NotesUI,
    viewModel: MainViewModel,
    scope: CoroutineScope,
    drawerStateHost: DrawerState,
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
                            scope.launch { drawerStateHost.open() }
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
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.onEvent(MainUIEvents.ClearTrashCan)
                                },
                                text = {
                                    Text(
                                        text = "Esvaziar lixeira",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            )
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
                                style = MaterialTheme.typography.headlineSmall
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
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.onEvent(MainUIEvents.DeleteNotes)
                                    },
                                    text = {
                                        Text(
                                            text = "Excluir definitivamente",
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.3.dp))
        }
    }
}