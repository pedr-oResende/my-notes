package br.com.mynotes.features.notes.presentation.screens.home

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.R
import br.com.mynotes.features.notes.domain.model.notes
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBar
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.presentation.screens.home.components.GridNotesList
import br.com.mynotes.features.notes.presentation.screens.home.components.LinearNotesList
import br.com.mynotes.features.notes.presentation.util.NotesEvent
import br.com.mynotes.ui.theme.MyNotesTheme
import coil.compose.rememberAsyncImagePainter

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
                TopBar(
                    title = "My notes",
                    backPressedDispatcher = onBackPressedDispatcher,
                    hasNavigationIcon = false,
                    actions = {
                        TopBarIcon(
                            onClick = {
                                viewModel.onEvent(NotesEvent.ToggleListView)
                            },
                            painter =
                            rememberAsyncImagePainter(
                                model = if (state.isInGridMode)
                                    R.drawable.ic_list_view
                                else
                                    R.drawable.ic_grid_view
                            ),
                            visibility = true
                        )
                        TopBarIcon(
                            onClick = { },
                            imageVector = Icons.Default.Delete,
                            visibility = state.isInSelectedMode
                        )
                    },
                )
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