package br.com.mynotes.features.notes.presentation.screens.note_detail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomEditText(
    text: String,
    placeholder: String,
    onValueChange: (value: String) -> Unit,
    textStyle: TextStyle,
    readOnly: Boolean = false
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        textStyle = textStyle,
        onValueChange = { newText ->
            onValueChange(newText)
        },
        placeholder = {
            Text(
                text = placeholder,
                style = textStyle,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            disabledTextColor = Color.Transparent,
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        readOnly = readOnly
    )
}
