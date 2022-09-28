package br.com.mynotes.features.notes.ui.screens.note_detail

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.Unarchive
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.R
import br.com.mynotes.commom.extensions.getArgument
import br.com.mynotes.commom.extensions.noRippleClickable
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.ui.compose.components.DefaultAlertDialog
import br.com.mynotes.features.notes.ui.compose.navigation.Screens
import br.com.mynotes.features.notes.ui.compose.theme.MyNotesTheme
import br.com.mynotes.features.notes.ui.compose.widgets.TopBar
import br.com.mynotes.features.notes.ui.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.ui.screens.note_detail.components.CustomEditText
import br.com.mynotes.features.notes.ui.screens.note_detail.state.NoteDetailUIEvents
import br.com.mynotes.features.notes.ui.screens.note_detail.state.NotesDetailActions
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    navHostController: NavHostController,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    viewModel: NoteDetailViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {
    MyNotesTheme {
        val noteDetailUI = viewModel.noteDetailUI.value
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(key1 = true) {
            viewModel.loadNote(
                note = navHostController.previousBackStackEntry?.savedStateHandle?.getArgument<Note>(
                    key = Screens.NoteDetail.argumentKey
                )
            )
            val callback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.onEvent(NoteDetailUIEvents.SaveNote)
                    if (isEnabled) {
                        isEnabled = false
                        navHostController.navigateUp()
                    }
                }
            }
            onBackPressedDispatcher.addCallback(lifecycleOwner, callback)
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is NotesDetailActions.ProcessNote -> {
                        Screens.NoteDetail.backToHome(navHostController)
                    }
                    is NotesDetailActions.ShowRestoreNoteSnackBar -> {
                        val result = snackbarHostState.showSnackbar(
                            message = event.text,
                            actionLabel = event.label
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(NoteDetailUIEvents.RestoreNote)
                        }
                    }
                    is NotesDetailActions.EmptyNote -> {
                        navHostController.navigate(
                            Screens.Home.passArgument(
                                argument = context.getString(R.string.empty_nome_message)
                            )
                        ) {
                            popUpTo(0)
                        }
                    }
                    is NotesDetailActions.DiscardNote -> {
                        Screens.NoteDetail.backToHome(navHostController)
                    }
                }
            }
        }
        val (showAlertDialog, setShowAlertDialog) = remember { mutableStateOf(false) }
        DefaultAlertDialog(
            text = stringResource(R.string.remove_note_alert_dialog_text),
            buttonText = stringResource(R.string.label_delete),
            onClick = {
                viewModel.onEvent(NoteDetailUIEvents.DeleteNote)
            },
            showDialog = showAlertDialog,
            setShowDialog = setShowAlertDialog
        )
        Scaffold(
            topBar = {
                TopBar(
                    actions = {
                        if (noteDetailUI.note?.isInTrashCan != true) {
                            TopBarIcon(
                                onClick = {
                                    viewModel.onEvent(NoteDetailUIEvents.ToggleMarkPin)
                                },
                                imageVector = if (noteDetailUI.isPinMarked)
                                    Icons.Filled.PushPin
                                else
                                    Icons.Outlined.PushPin
                            )
                            TopBarIcon(
                                onClick = {
                                    viewModel.onEvent(NoteDetailUIEvents.ArchiveNote)
                                },
                                imageVector = if (noteDetailUI.note?.isArchived == true) Icons.Outlined.Unarchive
                                else Icons.Outlined.Archive
                            )
                            TopBarIcon(
                                onClick = {
                                    setShowAlertDialog(true)
                                },
                                imageVector = Icons.Outlined.Delete
                            )
                        }
                    },
                    navigationIcon = {
                        TopBarIcon(
                            onClick = { onBackPressedDispatcher.onBackPressed() },
                            imageVector = Icons.Default.ArrowBack
                        )
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
            ) {
                val isInTrashCan = noteDetailUI.note?.isInTrashCan ?: false
                if (isInTrashCan) {
                    Box(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .fillMaxSize()
                            .noRippleClickable {
                                viewModel.onEvent(NoteDetailUIEvents.TryToEditDeletedNote)
                            }
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    CustomEditText(
                        text = noteDetailUI.title,
                        placeholder = stringResource(id = R.string.note_title_placeholder),
                        onValueChange = {
                            viewModel.onEvent(NoteDetailUIEvents.TitleChanged(it))
                        },
                        textStyle = MaterialTheme.typography.headlineSmall,
                        readOnly = isInTrashCan
                    )
                    CustomEditText(
                        text = noteDetailUI.content,
                        placeholder = stringResource(id = R.string.note_content_placeholder),
                        onValueChange = {
                            viewModel.onEvent(NoteDetailUIEvents.ContentChanged(it))
                        },
                        textStyle = MaterialTheme.typography.bodyLarge,
                        readOnly = isInTrashCan
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                if (noteDetailUI.note != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                            .align(Alignment.BottomCenter)
                            .background(color = MaterialTheme.colorScheme.surface),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(
                                id = R.string.created_at_label,
                                noteDetailUI.note.createAt
                            ),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}