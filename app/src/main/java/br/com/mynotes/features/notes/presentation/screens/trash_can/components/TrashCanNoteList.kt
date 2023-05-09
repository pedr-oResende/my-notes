package br.com.mynotes.features.notes.presentation.screens.trash_can.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.compose.components.NotesList
import br.com.mynotes.features.notes.presentation.screens.main.ui.NoteListState
import br.com.mynotes.features.notes.presentation.screens.trash_can.TrashCanViewModel

@Composable
fun TrashCanNoteList(
    notes: List<Note>,
    viewModel: TrashCanViewModel,
    navHostController: NavHostController
) {
    NotesList(
        noteListState = NoteListState.Grid,
        notes = notes,
        onItemClick = { note ->
            viewModel.onItemClick(note, navHostController)
        },
        onItemLongClick = { note ->
            viewModel.onItemLongClick(note)
        }
    )
}