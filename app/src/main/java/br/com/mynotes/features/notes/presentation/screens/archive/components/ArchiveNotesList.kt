package br.com.mynotes.features.notes.presentation.screens.archive.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.presentation.compose.components.NotesList
import br.com.mynotes.features.notes.presentation.screens.archive.ArchiveViewModel
import br.com.mynotes.features.notes.presentation.screens.main.ui.NoteListState

@Composable
fun ArchiveNotesList(
    viewModel: ArchiveViewModel,
    navHostController: NavHostController,
    noteListState: NoteListState
) {
    val notes = viewModel.getNotesListFilteredByText()
    NotesList(
        noteListState = noteListState,
        notes = notes,
        onItemClick = { note ->
            viewModel.onItemClick(note, navHostController)
        },
        onItemLongClick = { note ->
            viewModel.onItemLongClick(note)
        }
    )
}