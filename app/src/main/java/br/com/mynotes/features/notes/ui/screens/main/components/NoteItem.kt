package br.com.mynotes.features.notes.ui.screens.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.mynotes.features.notes.domain.model.Note

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note
) {
    Box(
        modifier = modifier.background(
            color = MaterialTheme.colors.primary.copy(
                alpha = if (note.isSelected) 0.6f else 1f
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.h5,
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