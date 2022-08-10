package br.com.mynotes.features.notes.presentation.compose.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DefaultRadioButton(
    modifier: Modifier,
    text: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary,
                unselectedColor = MaterialTheme.colors.onBackground
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.h6)
    }
}

@Preview
@Composable
private fun RadioButtonPreview() {
    Column {
        DefaultRadioButton(
            modifier = Modifier.wrapContentWidth(),
            text = "Button 1",
            selected = true
        ) { }
        DefaultRadioButton(
            modifier = Modifier.wrapContentWidth(),
            text = "Button 2",
            selected = false
        ) { }
    }
}