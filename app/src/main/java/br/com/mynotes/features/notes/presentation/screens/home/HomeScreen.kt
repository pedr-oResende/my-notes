package br.com.mynotes.features.notes.presentation.screens.home

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.R
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.compose.components.DrawerBody
import br.com.mynotes.features.notes.presentation.compose.components.DrawerHeader
import br.com.mynotes.features.notes.presentation.compose.components.NotesList
import br.com.mynotes.features.notes.presentation.compose.navigation.Screens
import br.com.mynotes.features.notes.presentation.compose.widgets.CustomEditText
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBar
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.presentation.model.MenuItem
import br.com.mynotes.features.notes.presentation.util.HomeEvent
import br.com.mynotes.ui.theme.MyNotesTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    onBackPressedDispatcher: OnBackPressedDispatcher,
    scaffoldState: ScaffoldState,
    snackBarMessage: String
) {
    val notesUI = viewModel.notesUI.value
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        if (snackBarMessage.isNotBlank()) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = snackBarMessage
            )
        }
    }
    MyNotesTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
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
                                    TopBarIcon(
                                        onClick = {
                                            viewModel.onEvent(HomeEvent.ToggleCloseSelection)
                                        },
                                        imageVector = Icons.Filled.Close
                                    )
                                    Row {
                                        TopBarIcon(
                                            onClick = {
                                                viewModel.onEvent(HomeEvent.ToggleMarkPin)
                                            },
                                            imageVector = if (notesUI.isPinFilled) {
                                                Icons.Filled.PushPin
                                            } else {
                                                Icons.Outlined.PushPin
                                            }
                                        )
                                        TopBarIcon(
                                            onClick = {
                                                viewModel.onEvent(HomeEvent.ToggleMenuMore)
                                            },
                                            imageVector = Icons.Filled.MoreVert
                                        )
                                        DropdownMenu(
                                            expanded = notesUI.showMenuMore,
                                            onDismissRequest = { viewModel.onEvent(HomeEvent.ToggleMenuMore) }) {
                                            DropdownMenuItem(onClick = {
                                                viewModel.onEvent(HomeEvent.ArchiveNote)
                                            }) {
                                                Text(
                                                    text = stringResource(R.string.dropdown_label_archive),
                                                    style = MaterialTheme.typography.body1
                                                )
                                            }
                                            DropdownMenuItem(onClick = {
                                                viewModel.onEvent(HomeEvent.DeleteNote)
                                            }) {
                                                Text(
                                                    text = stringResource(R.string.dropdown_label_delete),
                                                    style = MaterialTheme.typography.body1
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(12.3.dp))
                    }
                }
                AnimatedVisibility(
                    visible = !notesUI.isInSelectedMode,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    CustomEditText(
                        modifier = Modifier
                            .padding(top = 18.dp, start = 16.dp, end = 16.dp)
                            .clip(RoundedCornerShape(50)),
                        placeholder = stringResource(R.string.search_note_placeholder),
                        value = notesUI.searchNotesText,
                        onValueChange = { newText ->
                            viewModel.onEvent(HomeEvent.SearchTextChanged(newText))
                        },
                        leadingIcon = {
                            TopBarIcon(
                                onClick = {
                                    scope.launch {
                                        scaffoldState.drawerState.open()
                                    }
                                },
                                imageVector = Icons.Filled.Menu
                            )
                        },
                        trailingIcon = {
                            Row {
                                TopBarIcon(
                                    onClick = {
                                        viewModel.onEvent(HomeEvent.ToggleListView)
                                    },
                                    imageVector = if (notesUI.isInGridMode)
                                        Icons.Outlined.ViewAgenda
                                    else
                                        Icons.Outlined.GridView
                                )
                            }
                        }
                    )
                }
            },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = viewModel.notesUI.value.screenState == ScreenState.HomeScreen,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    FloatingActionButton(
                        onClick = {
                            Screens.NoteDetail.navigate(navHostController)
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
                            viewModel.onEvent(HomeEvent.ChangeScreen(item.screen))
                        }
                    },
                    currentRoute = notesUI.screenState
                )
            }
        ) { padding ->
            val onItemClick: (Note) -> Unit = { note ->
                if (notesUI.isInSelectedMode)
                    viewModel.onEvent(HomeEvent.SelectNote(note))
                else
                    viewModel.goToDetail(
                        navHostController = navHostController,
                        note = note
                    )
            }
            val onItemLongClick: (Note) -> Unit = { note ->
                viewModel.onEvent(HomeEvent.SelectNote(note))
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
            ) {
                when (notesUI.screenState) {
                    ScreenState.HomeScreen -> {
                        MainListScreen(
                            viewModel = viewModel,
                            scaffoldState = scaffoldState,
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

                    }
                }
            }
        }
    }
}

@Composable
fun MainListScreen(
    viewModel: HomeViewModel,
    scaffoldState: ScaffoldState,
    onItemClick: (Note) -> Unit,
    onItemLongClick: (Note) -> Unit
) {
    val notesUI = viewModel.notesUI.value
    val notes = viewModel.getNotesListFiltered()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is NotesEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is NotesEvents.ShowUndoSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.text,
                        actionLabel = event.label
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(HomeEvent.RestoreNotes)
                    }
                }
            }
        }
    }
    val fixedNotes = notes.filter { it.isFixed }
    val otherNotes = notes.filter { !it.isFixed }
    if (fixedNotes.isNotEmpty()) {
        Text(
            text = stringResource(R.string.notes_list_fixed_label),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )
        NotesList(
            isInGridMode = notesUI.isInGridMode,
            notes = fixedNotes,
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick
        )
        if (otherNotes.isNotEmpty()) {
            Text(
                text = stringResource(R.string.notes_list_others_label),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = 16.dp)
            )
            NotesList(
                isInGridMode = notesUI.isInGridMode,
                notes = otherNotes,
                onItemClick = onItemClick,
                onItemLongClick = onItemLongClick
            )
        }
    } else {
        NotesList(
            isInGridMode = notesUI.isInGridMode,
            notes = notes,
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick
        )
    }
}

@Composable
fun ArchiveListScreen(
    viewModel: HomeViewModel,
    scaffoldState: ScaffoldState,
    onItemClick: (Note) -> Unit,
    onItemLongClick: (Note) -> Unit
) {
    val notesUI = viewModel.notesUI.value
    val notes = viewModel.getNotesListFiltered()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is NotesEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is NotesEvents.ShowUndoSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.text,
                        actionLabel = event.label
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(HomeEvent.RestoreNotes)
                    }
                }
            }
        }
    }
    NotesList(
        isInGridMode = notesUI.isInGridMode,
        notes = notes,
        onItemClick = onItemClick,
        onItemLongClick = onItemLongClick
    )

}