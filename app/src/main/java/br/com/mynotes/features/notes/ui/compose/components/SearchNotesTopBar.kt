package br.com.mynotes.features.notes.ui.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import br.com.mynotes.R
import br.com.mynotes.commom.util.PreferencesKey
import br.com.mynotes.commom.util.PreferencesWrapper
import br.com.mynotes.features.notes.ui.compose.widgets.CustomEditText
import br.com.mynotes.features.notes.ui.compose.widgets.TopBarIcon
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
    trailingIcon: @Composable (() -> Unit)? = null,
    isInGridMode: MutableState<Boolean>
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
                    style = MaterialTheme.typography.bodyMedium,
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
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        value = value,
        onValueChange = onValueChange,
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        leadingIcon = leadingIcon,
        trailingIcon = {
                Row {
                    TopBarIcon(
                        onClick = {
                            isInGridMode.value = !isInGridMode.value
                            PreferencesWrapper.instance?.putBoolean(
                                key = PreferencesKey.NOTE_LIST_TYPE_STATE_KEY,
                                value = isInGridMode.value
                            )
                        },
                        imageVector = if (isInGridMode.value)
                            Icons.Outlined.ViewAgenda
                        else
                            Icons.Outlined.GridView
                    )
                    if (trailingIcon != null) {
                        trailingIcon()
                    }
                }
        },
        isError = isError,
        keyboardType = keyboardType,
        visualTransformation = visualTransformation
    )
}