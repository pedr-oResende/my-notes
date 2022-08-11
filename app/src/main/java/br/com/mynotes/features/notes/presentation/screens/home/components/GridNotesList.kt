package br.com.mynotes.features.notes.presentation.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.compose.widgets.StaggeredVerticalGrid
import br.com.mynotes.features.notes.presentation.screens.home.HomeViewModel
import br.com.mynotes.features.notes.presentation.util.NotesEvent

@Composable
fun GridNotesList(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    viewModel: HomeViewModel,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        StaggeredVerticalGrid(modifier = modifier.padding(horizontal = 8.dp)) {
            notes.forEach { note ->
                NoteItem(
                    modifier = Modifier.padding(all = 8.dp),
                    note = note,
                    isSelected = viewModel.isNoteSelected(note),
                    onClick = {
                        if (viewModel.state.value.isInSelectedMode)
                            viewModel.onEvent(NotesEvent.SelectNote(note.id))
                        else
                            onClick()
                    },
                    onLongClick = {
                        viewModel.onItemLongClick(note.id)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}