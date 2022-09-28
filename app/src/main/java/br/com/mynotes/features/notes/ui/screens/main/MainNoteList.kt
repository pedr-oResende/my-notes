package br.com.mynotes.features.notes.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.ui.compose.navigation.Screens
import br.com.mynotes.features.notes.ui.compose.theme.MyNotesTheme
import br.com.mynotes.features.notes.ui.screens.archive.ArchiveListScreen
import br.com.mynotes.features.notes.ui.screens.home.HomeList
import br.com.mynotes.features.notes.ui.screens.main.components.NotesListTopBar
import br.com.mynotes.features.notes.ui.screens.main.state.MainUIEvents
import br.com.mynotes.features.notes.ui.screens.main.state.NotesActions
import br.com.mynotes.features.notes.ui.screens.main.state.ScreenState
import br.com.mynotes.features.notes.ui.screens.trash_can.TrashCanListScreen
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNoteListScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    drawerStateHost: DrawerState
) {
    val notesUI = viewModel.notesUI.value
    LaunchedEffect(key1 = true) {
        val emptyNoteMessage = viewModel.getSnackBarMessage()
        if (emptyNoteMessage.isNotBlank()) {
            snackbarHostState.showSnackbar(
                message = emptyNoteMessage
            )
        }
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is NotesActions.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is NotesActions.ShowUndoSnackBar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.text,
                        actionLabel = event.label
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(MainUIEvents.RestoreNotes)
                    }
                }
            }
        }
    }
    MyNotesTheme {
        Scaffold(
            topBar = {
                NotesListTopBar(
                    viewModel = viewModel,
                    drawerStateHost = drawerStateHost
                )
            },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = viewModel.notesUI.value.screenState == ScreenState.HomeScreen,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    FloatingActionButton(
                        onClick = {
                            navHostController.navigate(Screens.NoteDetail.route)
                        },
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
                    }
                }
            }
        ) { padding ->
            val onItemClick: (Note) -> Unit = { note ->
                if (notesUI.isInSelectedMode)
                    viewModel.onEvent(MainUIEvents.SelectNote(note))
                else
                    Screens.NoteDetail.navigateWithArgument(
                        navHostController = navHostController,
                        argumentValue = note
                    )
            }
            val onItemLongClick: (Note) -> Unit = { note ->
                viewModel.onEvent(MainUIEvents.SelectNote(note))
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
            ) {
                when (notesUI.screenState) {
                    ScreenState.HomeScreen -> {
                        HomeList(
                            viewModel = viewModel,
                            onItemClick = onItemClick,
                            onItemLongClick = onItemLongClick
                        )
                    }
                    ScreenState.ArchiveScreen -> {
                        ArchiveListScreen(
                            viewModel = viewModel,
                            snackbarHostState = snackbarHostState,
                            onItemClick = onItemClick,
                            onItemLongClick = onItemLongClick
                        )
                    }
                    ScreenState.TrashCanScreen -> {
                        TrashCanListScreen(
                            viewModel = viewModel,
                            notesUI = notesUI,
                            onItemClick = onItemClick,
                            onItemLongClick = onItemLongClick
                        )
                    }
                }
            }
        }
    }
}