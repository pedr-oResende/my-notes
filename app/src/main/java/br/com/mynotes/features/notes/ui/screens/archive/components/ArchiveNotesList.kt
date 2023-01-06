package br.com.mynotes.features.notes.ui.screens.archive.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.ui.compose.components.NotesList
import br.com.mynotes.features.notes.ui.screens.archive.ArchiveViewModel

@Composable
fun ArchiveNotesList(
    viewModel: ArchiveViewModel,
    navHostController: NavHostController,
    isInGridMode: Boolean
) {
    val notes = viewModel.getNotesListFilteredByText()
    NotesList(
        isInGridMode = isInGridMode,
        notes = notes,
        onItemClick = { note ->
            viewModel.onItemClick(note, navHostController)
        },
        onItemLongClick = { note ->
            viewModel.onItemLongClick(note)
        }
    )
}