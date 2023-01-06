package br.com.mynotes.features.notes.ui.screens.home.components

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
import br.com.mynotes.features.notes.ui.compose.animations.FadeTransition
import br.com.mynotes.features.notes.ui.compose.components.SearchNotesTopBar
import br.com.mynotes.features.notes.ui.compose.widgets.TopBar
import br.com.mynotes.features.notes.ui.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.ui.screens.home.HomeViewModel
import br.com.mynotes.features.notes.ui.screens.home.ui.HomeEvents
import br.com.mynotes.features.notes.ui.screens.main.ui.MainUIEvents

@Composable
fun HomeTopBar(
    viewModel: HomeViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    isInGridMode: MutableState<Boolean>
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
                                onClick = { viewModel.onEvent(HomeEvents.ToggleMarkPin) },
                                imageVector = if (notesUI.isPinFilled)
                                    Icons.Filled.PushPin
                                else
                                    Icons.Outlined.PushPin
                            )
                            TopBarIcon(
                                onClick = {
                                    toggleMenuMore(showMenuMore)
                                },
                                imageVector = Icons.Filled.MoreVert
                            )
                            DropdownMenu(
                                expanded = showMenuMore.value,
                                onDismissRequest = { toggleMenuMore(showMenuMore) }) {
                                DropdownMenuItem(
                                    onClick = {
                                        toggleMenuMore(showMenuMore)
                                        viewModel.onEvent(HomeEvents.ArchiveNote)
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
                                        toggleMenuMore(showMenuMore)
                                        viewModel.onEvent(HomeEvents.MoveNoteToTrashCan)
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
            value = notesUI.searchNotesText,
            screenName = stringResource(id = R.string.menu_item_home),
            onValueChange = { newText ->
                viewModel.onEvent(MainUIEvents.SearchTextChanged(newText))
            },
            leadingIcon = {
                TopBarIcon(
                    onClick = openDrawer,
                    imageVector = Icons.Filled.Menu
                )
            },
            isInGridMode = isInGridMode
        )
    }
}

fun toggleMenuMore(showMenuMore: MutableState<Boolean>) {
    showMenuMore.value = !showMenuMore.value
}