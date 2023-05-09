package br.com.mynotes.features.notes.presentation.screens.archive.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.mynotes.R
import br.com.mynotes.features.notes.presentation.compose.animations.FadeTransition
import br.com.mynotes.features.notes.presentation.compose.components.SearchNotesTopBar
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBar
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.presentation.screens.archive.ArchiveViewModel
import br.com.mynotes.features.notes.presentation.screens.archive.ui.ArchiveEvents
import br.com.mynotes.features.notes.presentation.screens.home.components.toggleMenuMore
import br.com.mynotes.features.notes.presentation.screens.main.ui.MainUIEvents
import br.com.mynotes.features.notes.presentation.screens.main.ui.NoteListState

@Composable
fun ArchiveTopBar(
    viewModel: ArchiveViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    noteListState: MutableState<NoteListState>
) {
    val showMenuMore = remember { mutableStateOf(false) }
    val notesUI = viewModel.notesUI.value
    FadeTransition(visible = notesUI.isInSelectedMode) {
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
                                onClick = { viewModel.onEvent(ArchiveEvents.ToggleMarkPin) },
                                imageVector = if (notesUI.isPinFilled) {
                                    Icons.Filled.PushPin
                                } else {
                                    Icons.Outlined.PushPin
                                }
                            )
                            TopBarIcon(
                                onClick = { toggleMenuMore(showMenuMore) },
                                imageVector = Icons.Filled.MoreVert
                            )
                            DropdownMenu(
                                expanded = showMenuMore.value,
                                onDismissRequest = { toggleMenuMore(showMenuMore) }) {
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.onEvent(ArchiveEvents.UnArchiveNote)
                                    },
                                    text = {
                                        Text(
                                            text = stringResource(R.string.label_unarchive),
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.onEvent(ArchiveEvents.MoveNoteToTrashCan)
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
            Spacer(modifier = Modifier.height(4.5.dp))
        }
    }
    FadeTransition(visible = notesUI.isInSelectedMode.not()) {
        SearchNotesTopBar(
            modifier = Modifier
                .padding(top = 18.dp, start = 16.dp, end = 16.dp),
            placeholder = stringResource(R.string.search_note_placeholder),
            value = notesUI.searchNotesText,
            screenName = stringResource(id = R.string.menu_item_archive),
            onValueChange = { newText ->
                viewModel.onEvent(MainUIEvents.SearchTextChanged(newText))
            },
            leadingIcon = {
                TopBarIcon(
                    onClick = openDrawer,
                    imageVector = Icons.Filled.Menu
                )
            },
            noteListState = noteListState
        )
    }
}