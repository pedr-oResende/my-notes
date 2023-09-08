package br.com.mynotes.features.notes.presentation.compose.animations

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun CustomSwipeToDismiss(
    onDismiss: () -> Unit,
    enabled: Boolean = true,
    content: @Composable (alpha: Float) -> Unit
) {
    val limit = 180f
    var offset by remember { mutableFloatStateOf(0f) }
    val state = rememberDraggableState {
        if (enabled) {
            offset += it
        } else {
            if (offset.absoluteValue <= 100f) offset += it
        }
    }
    Box(
        modifier = Modifier
            .offset {
                IntOffset(offset.roundToInt(), 0)
            }
            .draggable(
                orientation = Orientation.Horizontal,
                state = state,
                onDragStopped = {
                    if (offset.absoluteValue >= limit && enabled) {
                        onDismiss()
                        offset = 0f
                    }
                    animateBack(
                        start = offset
                    ) { offset = it }
                }
            )
    ) {
        content(alpha = 1f - (offset / 300f).absoluteValue)
    }
}

private suspend fun animateBack(
    start: Float,
    onAnimate: (Float) -> Unit
) {
    val speed = 1L
    if (start > 0) {
        goToLeft(start, onAnimate, limit = 0f, speed = speed)
    } else {
        goToRight(start, onAnimate, limit = 0f, speed = speed)
    }
}

private suspend fun goToLeft(start: Float, onAnimate: (Float) -> Unit, limit: Float, speed: Long) {
    var offset = start
    while (offset > limit) {
        offset -= 5
        onAnimate(offset)
        delay(speed)
    }
    onAnimate(0f)
}

private suspend fun goToRight(
    start: Float,
    onAnimate: (Float) -> Unit,
    limit: Float,
    speed: Long
) {
    var offset = start
    while (offset < limit) {
        offset += 5
        onAnimate(offset)
        delay(speed)
    }
    onAnimate(0f)
}