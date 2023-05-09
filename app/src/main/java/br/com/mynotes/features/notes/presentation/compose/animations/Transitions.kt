package br.com.mynotes.features.notes.presentation.compose.animations

import androidx.compose.animation.*
import androidx.compose.runtime.Composable

@Composable
fun FadeTransition(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        content()
    }
}

@Composable
fun SlideTransition(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandIn(),
        exit = shrinkOut()
    ) {
        content()
    }
}