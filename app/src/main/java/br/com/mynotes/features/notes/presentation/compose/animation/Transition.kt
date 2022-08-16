package br.com.mynotes.features.notes.presentation.compose.animation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

internal fun exitTransition(width: Int) =
    slideOutHorizontally(
        targetOffsetX = { width },
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

internal fun enterTransition(width: Int) =
    slideInHorizontally(
        initialOffsetX = { width },
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )
