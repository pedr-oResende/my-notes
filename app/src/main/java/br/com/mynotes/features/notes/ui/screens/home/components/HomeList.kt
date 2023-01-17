package br.com.mynotes.features.notes.ui.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.mynotes.R
import br.com.mynotes.features.notes.ui.compose.components.NotesList
import br.com.mynotes.features.notes.ui.screens.home.HomeViewModel

@Composable
fun HomeList(
    viewModel: HomeViewModel,
    navHostController: NavHostController,
    isInGridMode: Boolean
) {
    val notes = viewModel.getNotesListFilteredByText()
    val fixedNotes = notes.filter { it.isFixed }
    val otherNotes = notes.filter { !it.isFixed }
    if (fixedNotes.isNotEmpty()) {
        Column {
            Text(
                text = stringResource(R.string.notes_list_fixed_label),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            NotesList(
                isInGridMode = isInGridMode,
                notes = fixedNotes,
                onItemClick = { note ->
                    viewModel.onItemClick(note, navHostController)
                },
                onItemLongClick = { note ->
                    viewModel.onItemLongClick(note)
                }
            )
            if (otherNotes.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.notes_list_others_label),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp)
                )
                NotesList(
                    isInGridMode = isInGridMode,
                    notes = otherNotes,
                    onItemClick = { note ->
                        viewModel.onItemClick(note, navHostController)
                    },
                    onItemLongClick = { note ->
                        viewModel.onItemLongClick(note)
                    }
                )
            }
        }
    } else {
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
}