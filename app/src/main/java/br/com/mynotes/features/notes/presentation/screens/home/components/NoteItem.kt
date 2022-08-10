package br.com.mynotes.features.notes.presentation.screens.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.model.notes
import br.com.mynotes.ui.theme.MyNotesTheme

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
                .padding(end = 32.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFE0E0E0)
@Composable
private fun NoteItemPreview() {
    MyNotesTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteItem(note = notes[1])
        }
    }
}