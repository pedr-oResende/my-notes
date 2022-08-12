package br.com.mynotes.features.notes.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.material.icons.rounded.PushPin
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.R
import br.com.mynotes.commom.compose.navigation.Screens
import br.com.mynotes.commom.compose.widgets.TopBar
import br.com.mynotes.commom.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.presentation.screens.home.components.GridNotesList
import br.com.mynotes.features.notes.presentation.screens.home.components.LinearNotesList
import br.com.mynotes.features.notes.presentation.util.NotesEvent
import br.com.mynotes.ui.theme.MyNotesTheme

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    MyNotesTheme {
        val state = viewModel.state.value
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            topBar = {
                if (state.isInSelectedMode) {
                    TopBar(
                        actions = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TopBarIcon(
                                    onClick = {
                                        viewModel.onEvent(NotesEvent.ToggleCloseSelection(cleanList = true))
                                    },
                                    imageVector = Icons.Default.Close
                                )
                                Row {
                                    TopBarIcon(
                                        onClick = {
                                            viewModel.onEvent(NotesEvent.ToggleMarkPin)
                                        },
                                        imageVector = if (state.togglePin)
                                            Icons.Rounded.PushPin
                                        else
                                            Icons.Outlined.PushPin
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
                        title = stringResource(id = R.string.app_name),
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
                                onClick = {
                                    Screens.NoteDetail.navigate(navHostController)
                                },
                                imageVector = Icons.Default.Edit
                            )
                        }
                    )
                }
            },
            scaffoldState = scaffoldState
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
            ) {
                val notes = state.notes
                if (state.isInGridMode) {
                    GridNotesList(
                        notes = notes,
                        viewModel = viewModel,
                        navHostController = navHostController
                    )
                } else {
                    LinearNotesList(
                        notes = notes,
                        viewModel = viewModel,
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}

//viewModel.onEvent(NotesEvent.OnNoteDeleted) onDelete
//viewModel.onEvent(NotesEvent.ToggleCloseSelection(cleanList = false))
//val result = scaffoldState.snackbarHostState.showSnackbar(
//    message = "Nota removida com sucesso!",
//    actionLabel = "Desfazer"
//)
//when (result) {
//    SnackbarResult.ActionPerformed -> {
//        viewModel.onEvent(NotesEvent.RestoreNotes)
//        viewModel.cleanSelectedList()
//    }
//    SnackbarResult.Dismissed -> {
//        viewModel.cleanSelectedList()
//        setShowSnackBar(false)
//    }
//}