package br.com.mynotes.features.notes.ui.screens.home.components

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
import br.com.mynotes.features.notes.ui.screens.home.HomeViewModel
import br.com.mynotes.features.notes.ui.screens.home.ui.HomeEvents
import br.com.mynotes.features.notes.ui.screens.main.state.MainUIEvents
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    viewModel: HomeViewModel,
    drawerStateHost: DrawerState,
) {
    val notesUI = viewModel.notesUI.value
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
                                onClick = { viewModel.onEvent(HomeEvents.ToggleMarkPin) },
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
                                        viewModel.onEvent(MainUIEvents.ToggleMenuMore)
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
    AnimatedVisibility(
        visible = !notesUI.isInSelectedMode,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
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
                    onClick = {
                        scope.launch { drawerStateHost.open() }
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