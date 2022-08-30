package br.com.mynotes.features.notes.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.R
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.ui.compose.components.DrawerBody
import br.com.mynotes.features.notes.ui.compose.components.DrawerHeader
import br.com.mynotes.features.notes.ui.compose.navigation.Screens
import br.com.mynotes.features.notes.ui.compose.theme.MyNotesTheme
import br.com.mynotes.features.notes.ui.model.MenuItem
import br.com.mynotes.features.notes.ui.screens.archive.ArchiveListScreen
import br.com.mynotes.features.notes.ui.screens.home.HomeList
import br.com.mynotes.features.notes.ui.screens.main.components.NotesListTopBar
import br.com.mynotes.features.notes.ui.screens.main.state.MainUIEvents
import br.com.mynotes.features.notes.ui.screens.main.state.NotesActions
import br.com.mynotes.features.notes.ui.screens.main.state.ScreenState
import br.com.mynotes.features.notes.ui.screens.trash_can.TrashCanListScreen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun MainNoteListScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val notesUI = viewModel.notesUI.value
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        val emptyNoteMessage = viewModel.getSnackBarMessage()
        if (emptyNoteMessage.isNotBlank()) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = emptyNoteMessage
            )
        }
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is NotesActions.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is NotesActions.ShowUndoSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
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
            scaffoldState = scaffoldState,
            topBar = {
                NotesListTopBar(viewModel = viewModel, scaffoldState = scaffoldState)
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
                        backgroundColor = MaterialTheme.colors.surface,
                        contentColor = MaterialTheme.colors.primary
                    ) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
                    }
                }
            },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerShape = RectangleShape,
            drawerContent = {
                DrawerHeader()
                DrawerBody(
                    items = listOf(
                        MenuItem(
                            screen = ScreenState.HomeScreen,
                            title = stringResource(R.string.menu_item_home),
                            icon = Icons.Outlined.Home
                        ),
                        MenuItem(
                            screen = ScreenState.ArchiveScreen,
                            title = stringResource(R.string.menu_item_archive),
                            icon = Icons.Outlined.Archive
                        ),
                        MenuItem(
                            screen = ScreenState.TrashCanScreen,
                            title = stringResource(R.string.menu_item_trash_can),
                            icon = Icons.Outlined.Delete
                        ),
                    ),
                    onItemClick = { item ->
                        scope.launch {
                            scaffoldState.drawerState.close()
                            viewModel.onEvent(MainUIEvents.ChangeScreen(item.screen))
                        }
                    },
                    currentRoute = notesUI.screenState
                )
            }
        ) { padding ->
            val onItemClick: (Note) -> Unit = { note ->
                if (notesUI.isInSelectedMode)
                    viewModel.onEvent(MainUIEvents.SelectNote(note))
                else
                    viewModel.goToDetail(
                        navHostController = navHostController,
                        note = note
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
                            scaffoldState = scaffoldState,
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