package br.com.mynotes.features.notes.ui.screens.archive.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.mynotes.R
import br.com.mynotes.features.notes.ui.compose.components.SearchNotesTopBar
import br.com.mynotes.features.notes.ui.compose.widgets.TopBar
import br.com.mynotes.features.notes.ui.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.ui.screens.archive.ArchiveViewModel
import br.com.mynotes.features.notes.ui.screens.archive.ui.ArchiveEvents
import br.com.mynotes.features.notes.ui.screens.main.ui.MainUIEvents
import br.com.mynotes.features.notes.ui.screens.main.ui.NotesUI
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveTopBar(
    notesUI: NotesUI,
    viewModel: ArchiveViewModel,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
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
                                onClick = { viewModel.onEvent(ArchiveEvents.ToggleMarkPin) },
                                imageVector = if (notesUI.isPinFilled) {
                                    Icons.Filled.PushPin
                                } else {
                                    Icons.Outlined.PushPin
                                }
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
    AnimatedVisibility(
        visible = !notesUI.isInSelectedMode,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
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
                    onClick = {
                        scope.launch { drawerState.open() }
                    },
                    imageVector = Icons.Filled.Menu
                )
            },
            trailingIcon = {
                Row {
                    TopBarIcon(
                        onClick = { viewModel.onEvent(MainUIEvents.ToggleListView) },
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