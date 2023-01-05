package br.com.mynotes.features.notes.ui.screens.trash_can

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.ui.compose.theme.MyNotesTheme
import br.com.mynotes.features.notes.ui.screens.trash_can.components.TrashCanNoteList
import br.com.mynotes.features.notes.ui.screens.trash_can.components.TrashCanTopBar
import br.com.mynotes.features.notes.ui.screens.trash_can.ui.TrashCanEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashCanListScreen(
    navHostController: NavHostController,
    viewModel: TrashCanViewModel = hiltViewModel(),
    drawerStateHost: DrawerState
) {
    val notesUI = viewModel.notesUI.value
    val trashCanUI = viewModel.trashCanUI.value
    MyNotesTheme {
        Scaffold(
            topBar = {
                TrashCanTopBar(
                    notesUI = notesUI,
                    viewModel = viewModel,
                    drawerStateHost = drawerStateHost
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
            ) {
               AnimatedVisibility(visible = trashCanUI.showAutoDeleteMessage) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(0.9f),
                                text = stringResource(br.com.mynotes.R.string.trash_can_auto_delete_message),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            IconButton(
                                modifier = Modifier.weight(0.1f),
                                onClick = { viewModel.onEvent(TrashCanEvents.CloseAutoDeleteMessage) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
                TrashCanNoteList(
                    notes = notesUI.notes,
                    navHostController = navHostController,
                    viewModel = viewModel
                )
            }
        }
    }
}