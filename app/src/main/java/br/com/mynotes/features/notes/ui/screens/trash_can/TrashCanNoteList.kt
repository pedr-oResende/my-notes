package br.com.mynotes.features.notes.ui.screens.trash_can

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.mynotes.R
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.ui.compose.components.NotesList
import br.com.mynotes.features.notes.ui.screens.main.MainViewModel
import br.com.mynotes.features.notes.ui.screens.main.state.MainUIEvents
import br.com.mynotes.features.notes.ui.screens.main.state.NotesUI

@Composable
fun TrashCanListScreen(
    notesUI: NotesUI,
    viewModel: MainViewModel,
    onItemClick: (Note) -> Unit,
    onItemLongClick: (Note) -> Unit
) {
    val notes = notesUI.notes
    AnimatedVisibility(visible = notesUI.showAutoDeleteMessage) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(0.9f),
                    text = stringResource(R.string.trash_can_auto_delete_message),
                    style = MaterialTheme.typography.body1
                )
                IconButton(
                    modifier = Modifier.weight(0.1f),
                    onClick = { viewModel.onEvent(MainUIEvents.CloseAutoDeleteMessage) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colors.onSurface,
                        contentDescription = null
                    )
                }
            }
        }
    }
    NotesList(
        isInGridMode = true,
        notes = notes,
        onItemClick = onItemClick,
        onItemLongClick = onItemLongClick
    )
}