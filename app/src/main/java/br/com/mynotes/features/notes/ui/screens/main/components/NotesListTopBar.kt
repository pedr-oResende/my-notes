package br.com.mynotes.features.notes.ui.screens.main.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.mynotes.R
import br.com.mynotes.features.notes.ui.compose.widgets.CustomEditText
import br.com.mynotes.features.notes.ui.compose.widgets.TopBarIcon
import br.com.mynotes.features.notes.ui.screens.archive.ArchiveTopBar
import br.com.mynotes.features.notes.ui.screens.home.HomeTopBar
import br.com.mynotes.features.notes.ui.screens.main.MainViewModel
import br.com.mynotes.features.notes.ui.screens.main.state.MainUIEvents
import br.com.mynotes.features.notes.ui.screens.main.state.ScreenState
import br.com.mynotes.features.notes.ui.screens.trash_can.TrashCanTopBar
import kotlinx.coroutines.launch

@Composable
fun NotesListTopBar(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
) {
    val notesUI = viewModel.notesUI.value
    val scope = rememberCoroutineScope()
    HomeTopBar(
        notesUI = notesUI,
        viewModel = viewModel
    )
    ArchiveTopBar(
        notesUI = notesUI,
        viewModel = viewModel
    )
    TrashCanTopBar(
        notesUI = notesUI,
        viewModel = viewModel,
        scope = scope,
        scaffoldState = scaffoldState
    )
    AnimatedVisibility(
        visible = !notesUI.isInSelectedMode && notesUI.screenState != ScreenState.TrashCanScreen,
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
                viewModel.onEvent(MainUIEvents.SearchTextChanged(newText))
            },
            leadingIcon = {
                TopBarIcon(
                    onClick = {
                        scope.launch { scaffoldState.drawerState.open() }
                    },
                    imageVector = Icons.Filled.Menu
                )
            },
            trailingIcon = {
                Row {
                    TopBarIcon(
                        onClick = { viewModel.onEvent(MainUIEvents.ToggleListView) },
                        imageVector = if (notesUI.isInGridMode)
                            Icons.Outlined.ViewAgenda
                        else
                            Icons.Outlined.GridView
                    )
                }
            }
        )
    }
}