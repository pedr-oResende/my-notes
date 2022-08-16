package br.com.mynotes.features.notes.presentation.compose.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomEditText(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String? = null,
    count: Int? = null,
    value: MutableState<String>,
    visualTransformation: VisualTransformation? = null,
    keyboardType: KeyboardType? = null,
    isError: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(25)),
        value = value.value,
        onValueChange = { newText ->
            if (count == null || newText.length <= count) {
                value.value = newText
            }
        },
        placeholder = {
            if (placeholder != null) Text(text = placeholder)
        },
        label = {
            Text(text = label)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType ?: KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.secondary,
            backgroundColor = MaterialTheme.colors.surface,
            focusedIndicatorColor = MaterialTheme.colors.secondary
        ),
        visualTransformation = visualTransformation ?: VisualTransformation.None,
        isError = isError,
        shape = RoundedCornerShape(8.dp),
        leadingIcon = if (leadingIcon != null) {
            { leadingIcon() }
        } else null,
        trailingIcon = if (trailingIcon != null) {
            { trailingIcon() }
        } else null
    )
}