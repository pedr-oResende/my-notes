package br.com.mynotes.features.notes.presentation.screens.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.screens.home.HomeViewModel
import br.com.mynotes.features.notes.presentation.util.NotesEvent

@Composable
fun LinearNotesList(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    viewModel: HomeViewModel,
    navHostController: NavHostController
) {
    LazyColumn(modifier = modifier) {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        items(notes) { note ->
            NoteItem(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                note = note,
                isSelected = viewModel.isNoteSelected(note),
                onClick = {
                    if (viewModel.notesUI.value.isInSelectedMode)
                        viewModel.onEvent(NotesEvent.SelectNote(note))
                    else
                        viewModel.goToDetail(
                            navHostController = navHostController,
                            note = note
                        )
                },
                onLongClick = {
                    viewModel.onItemLongClick(note)
                }
            )
        }
        item { Spacer(modifier = Modifier.height(8.dp)) }
    }
}