package br.com.mynotes.features.notes.presentation.compose.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import br.com.mynotes.commom.extensions.ifNull

@Composable
fun CustomEditText(
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    value: String,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    onValueChange: (value: String) -> Unit,
    visualTransformation: VisualTransformation? = null,
    keyboardType: KeyboardType? = null,
    isError: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        value = value,
        onValueChange = { newText ->
            onValueChange(newText)
        },
        placeholder = placeholder,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType ifNull KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            disabledContainerColor = backgroundColor,
            cursorColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedLabelColor = Color.Transparent,
        ),
        visualTransformation = visualTransformation ifNull VisualTransformation.None,
        isError = isError,
        leadingIcon = { leadingIcon?.invoke() },
        trailingIcon = { trailingIcon?.invoke() }
    )
}