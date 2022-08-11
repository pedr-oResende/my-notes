package br.com.mynotes.features.notes.presentation.screens.home

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.domain.model.notes
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBar
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.presentation.screens.home.components.GridNotesList
import br.com.mynotes.features.notes.presentation.screens.home.components.LinearNotesList
import br.com.mynotes.features.notes.presentation.util.NotesEvent
import br.com.mynotes.ui.theme.MyNotesTheme

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    viewModel: HomeViewModel = hiltViewModel(),
    updateHome: Boolean?
) {
    MyNotesTheme {
        val state = viewModel.state.value
        Scaffold(
            topBar = {
                if (state.isInSelectedMode) {
                    TopBar(
                        backPressedDispatcher = onBackPressedDispatcher,
                        hasNavigationIcon = false,
                        actions = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TopBarIcon(
                                    onClick = {
                                        viewModel.onEvent(NotesEvent.ToggleCloseSelection)
                                    },
                                    imageVector = Icons.Default.Close
                                )
                                Row {
                                    TopBarIcon(
                                        onClick = { },
                                        imageVector = Icons.Default.PushPin
                                    )
                                    TopBarIcon(
                                        onClick = { },
                                        imageVector = Icons.Default.MoreVert
                                    )
                                }
                            }
                        }
                    )
                } else {
                    TopBar(
                        title = "My notes",
                        backPressedDispatcher = onBackPressedDispatcher,
                        hasNavigationIcon = false,
                        actions = {
                            TopBarIcon(
                                onClick = {
                                    viewModel.onEvent(NotesEvent.ToggleListView)
                                },
                                imageVector = if (state.isInGridMode)
                                        Icons.Outlined.ViewAgenda
                                    else
                                        Icons.Outlined.GridView
                            )
                            TopBarIcon(
                                onClick = { },
                                imageVector = Icons.Default.Edit
                            )
                        }
                    )
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
            ) {
                if (updateHome == true) {
                    viewModel.refresh()
                }
                val onClick = {

                }
                if (state.isInGridMode) {
                    GridNotesList(
                        notes = notes,
                        viewModel = viewModel,
                        onClick = onClick
                    )
                } else {
                    LinearNotesList(
                        notes = notes,
                        viewModel = viewModel,
                        onClick = onClick
                    )
                }
            }
        }
    }
}