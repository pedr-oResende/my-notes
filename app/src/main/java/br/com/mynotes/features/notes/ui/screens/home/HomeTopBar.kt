package br.com.mynotes.features.notes.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.mynotes.R
import br.com.mynotes.features.notes.ui.compose.widgets.TopBar
import br.com.mynotes.features.notes.ui.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.ui.screens.main.MainViewModel
import br.com.mynotes.features.notes.ui.screens.main.state.MainUIEvents
import br.com.mynotes.features.notes.ui.screens.main.state.NotesUI
import br.com.mynotes.features.notes.ui.screens.main.state.ScreenState

@Composable
fun HomeTopBar(notesUI: NotesUI, viewModel: MainViewModel) {
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
                                onClick = { viewModel.onEvent(MainUIEvents.ToggleMarkPin) },
                                imageVector = if (notesUI.isPinFilled)
                                    Icons.Filled.PushPin
                                else
                                    Icons.Outlined.PushPin
                            )
                            TopBarIcon(
                                onClick = {
                                    viewModel.onEvent(MainUIEvents.ToggleMenuMore)
                                },
                                imageVector = Icons.Filled.MoreVert
                            )
                            DropdownMenu(
                                expanded = notesUI.showMenuMore,
                                onDismissRequest = { viewModel.onEvent(MainUIEvents.ToggleMenuMore) }) {
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.onEvent(MainUIEvents.ToggleMenuMore)
                                        viewModel.onEvent(MainUIEvents.ArchiveNote(archive = true))
                                    },
                                    text = {
                                        Text(
                                            text = stringResource(R.string.label_archive),
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.onEvent(MainUIEvents.ToggleMenuMore)
                                        viewModel.onEvent(MainUIEvents.MoveNoteToTrashCan)
                                    },
                                    text = {
                                        Text(
                                            text = stringResource(R.string.label_delete),
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
