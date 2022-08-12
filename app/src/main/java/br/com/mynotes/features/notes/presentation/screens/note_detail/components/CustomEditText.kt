package br.com.mynotes.features.notes.presentation.screens.note_detail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun CustomEditText(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String,
    onValueChange: (value: String) -> Unit,
    textStyle: TextStyle
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        textStyle = textStyle,
        onValueChange = { newText ->
            onValueChange(newText)
        },
        placeholder = {
            Text(text = placeholder, style = textStyle)
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onBackground,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
