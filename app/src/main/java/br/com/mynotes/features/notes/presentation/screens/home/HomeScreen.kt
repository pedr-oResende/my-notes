package br.com.mynotes.features.notes.presentation.screens.home

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                    title = "Your notes",
                    backPressedDispatcher = onBackPressedDispatcher,
                    hasNavigationIcon = false,
                    actions = {
                        TopBarIcon(
                            onClick = {
                                viewModel.onEvent(NotesEvent.ToggleListView)
                            },
                            icon = if (state.isInGridMode)
                                rememberAsyncImagePainter(model = R.drawable.ic_grid_view)
                            else
                                rememberAsyncImagePainter(model = R.drawable.ic_list_view),
                            visibility = true
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
                val onLongClick = {

                }
                if (state.isInGridMode) {
                    GridNotesList(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        notes = notes,
                        onClick = onClick,
                        onLongClick = onLongClick
                    )
                } else {
                    LinearNotesList(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        notes = notes,
                        onClick = onClick,
                        onLongClick = onLongClick
                    )
                }
            }
        }
    }
}