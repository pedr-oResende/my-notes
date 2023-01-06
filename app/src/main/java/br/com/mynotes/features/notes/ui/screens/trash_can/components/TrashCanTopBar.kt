package br.com.mynotes.features.notes.ui.screens.trash_can.components

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.mynotes.R
import br.com.mynotes.features.notes.ui.compose.widgets.TopBar
import br.com.mynotes.features.notes.ui.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.ui.screens.main.ui.MainUIEvents
import br.com.mynotes.features.notes.ui.screens.main.ui.NotesUI
import br.com.mynotes.features.notes.ui.screens.trash_can.TrashCanViewModel
import br.com.mynotes.features.notes.ui.screens.trash_can.ui.TrashCanEvents
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashCanTopBar(
    notesUI: NotesUI,
    viewModel: TrashCanViewModel,
    drawerStateHost: DrawerState,
) {
    val scope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = !notesUI.isInSelectedMode,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column {
            TopBar(
                title = stringResource(id = R.string.menu_item_trash_can),
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
                                    viewModel.onEvent(TrashCanEvents.ClearTrashCan)
                                },
                                text = {
                                    Text(
                                        text = stringResource(id = R.string.clear_trash_can_label),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            )
                        }
                    }
                }
            )
        }
    }
    AnimatedVisibility(
        visible = notesUI.isInSelectedMode,
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
                                onClick = { viewModel.onEvent(TrashCanEvents.RestoreFromTrashCan) },
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
                                        viewModel.onEvent(TrashCanEvents.DeleteNotes)
                                    },
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.delete_note_label),
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}