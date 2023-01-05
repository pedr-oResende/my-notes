package br.com.mynotes.features.notes.ui.screens.archive.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.ui.compose.components.NotesList
import br.com.mynotes.features.notes.ui.screens.archive.ArchiveViewModel

@Composable
fun ArchiveNoteList(
    viewModel: ArchiveViewModel,
    navHostController: NavHostController
) {
    val notesUI = viewModel.notesUI.value
    val notes = viewModel.getNotesListFilteredByText()
    NotesList(
        isInGridMode = notesUI.isInGridMode,
        notes = notes,
        onItemClick = { note ->
            viewModel.onItemClick(note, navHostController)
        },
        onItemLongClick = { note ->
            viewModel.onItemLongClick(note)
        }
    )
}