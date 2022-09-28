package br.com.mynotes.features.notes.ui.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import br.com.mynotes.R
import br.com.mynotes.features.notes.ui.compose.widgets.CustomEditText
import kotlinx.coroutines.delay

@Composable
fun SearchNotesTopBar(
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.search_note_placeholder),
    screenName: String,
    value: String,
    onValueChange: (value: String) -> Unit,
    visualTransformation: VisualTransformation? = null,
    keyboardType: KeyboardType? = null,
    isError: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val showPlaceholder = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        delay(1500L)
        showPlaceholder.value = true
    }
    CustomEditText(
        modifier = modifier
            .clip(RoundedCornerShape(50)),
        placeholder = {
            AnimatedVisibility(
                visible = !showPlaceholder.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = screenName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            AnimatedVisibility(
                visible = showPlaceholder.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        value = value,
        onValueChange = onValueChange,
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        keyboardType = keyboardType,
        visualTransformation = visualTransformation
    )
}