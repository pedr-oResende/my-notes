package br.com.mynotes.features.notes.presentation.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.mynotes.R
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBar
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.presentation.screens.main.MainViewModel
import br.com.mynotes.features.notes.presentation.screens.main.NotesUI
import br.com.mynotes.features.notes.presentation.screens.main.ScreenState
import br.com.mynotes.features.notes.presentation.util.MainUIEvents

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
                                style = MaterialTheme.typography.h5
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
                                DropdownMenuItem(onClick = { viewModel.onEvent(MainUIEvents.ArchiveNote(archive = true)) }) {
                                    Text(
                                        text = stringResource(R.string.label_archive),
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                                DropdownMenuItem(onClick = { viewModel.onEvent(MainUIEvents.MoveNoteToTrashCan) }) {
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
