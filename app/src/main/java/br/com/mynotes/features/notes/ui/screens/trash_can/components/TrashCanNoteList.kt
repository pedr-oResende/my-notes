package br.com.mynotes.features.notes.ui.screens.trash_can.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.ui.compose.components.NotesList
import br.com.mynotes.features.notes.ui.screens.trash_can.TrashCanViewModel

@Composable
fun TrashCanNoteList(
    notes: List<Note>,
    viewModel: TrashCanViewModel,
    navHostController: NavHostController
) {
    NotesList(
        isInGridMode = true,
        notes = notes,
        onItemClick = { note ->
            viewModel.onItemClick(note, navHostController)
        },
        onItemLongClick = { note ->
            viewModel.onItemLongClick(note)
        }
    )
}