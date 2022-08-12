package br.com.mynotes.features.notes.presentation.screens.note_detail

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.rounded.PushPin
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.R
import br.com.mynotes.commom.compose.widgets.TopBar
import br.com.mynotes.commom.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.screens.note_detail.components.CustomEditText
import br.com.mynotes.features.notes.presentation.util.NoteDetailEvent
import br.com.mynotes.ui.theme.MyNotesTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NoteDetailScreen(
    navHostController: NavHostController,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    note: Note?,
    viewModel: NoteDetailViewModel = hiltViewModel()
) {
    MyNotesTheme {
        val state = viewModel.state.value
        val scaffoldState = rememberScaffoldState()
        LaunchedEffect(key1 = true) {
            viewModel.loadNote(note)
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    UIEvents.ProcessNote -> {
                        onBackPressedDispatcher.onBackPressed()
                    }
                    is UIEvents.ShowSnackBar -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message
                        )
                    }
                }
            }
        }
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(NoteDetailEvent.SaveNote)
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                ) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = null)
                }
            },
            topBar = {
                TopBar(
                    actions = {
                        TopBarIcon(
                            onClick = {
                                viewModel.onEvent(NoteDetailEvent.ToggleMarkPin)
                            },
                            imageVector = if (state.isPinMarked)
                                Icons.Rounded.PushPin
                            else
                                Icons.Outlined.PushPin
                        )
                        TopBarIcon(
                            onClick = {
                                viewModel.onEvent(NoteDetailEvent.ArchiveNote)
                            },
                            imageVector = Icons.Outlined.Archive
                        )
                        TopBarIcon(
                            onClick = {
                                viewModel.onEvent(NoteDetailEvent.DeleteNote)
                            },
                            imageVector = Icons.Outlined.Delete
                        )
                    },
                    onBackPressed = {
                        onBackPressedDispatcher.onBackPressed()
                    }
                )
            },
            scaffoldState = scaffoldState
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
            ) {
                Column(
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    CustomEditText(
                        text = state.title,
                        placeholder = stringResource(id = R.string.note_title_placeholder),
                        onValueChange = {
                            viewModel.onEvent(NoteDetailEvent.TitleChanged(it))
                        },
                        textStyle = MaterialTheme.typography.h6
                    )
                    CustomEditText(
                        text = state.content,
                        placeholder = stringResource(id = R.string.note_content_placeholder),
                        onValueChange = {
                            viewModel.onEvent(NoteDetailEvent.ContentChanged(it))
                        },
                        textStyle = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}