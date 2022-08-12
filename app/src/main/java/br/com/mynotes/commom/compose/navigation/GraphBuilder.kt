package br.com.mynotes.commom.compose.navigation

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.mynotes.commom.compose.animation.enterTransition
import br.com.mynotes.commom.compose.animation.exitTransition
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.screens.home.HomeScreen
import br.com.mynotes.features.notes.presentation.screens.note_detail.NoteDetailScreen
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.home(
    navHostController: NavHostController,
    width: Int
) {
    composable(
        route = Screens.Home.route,
        popExitTransition = { exitTransition(-width) },
        enterTransition = { enterTransition(-width) }
    ) {
        HomeScreen(
            navHostController = navHostController
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.noteDetail(
    navHostController: NavHostController,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    width: Int
) {
    composable(
        route = Screens.NoteDetail.route,
        popExitTransition = { exitTransition(width) },
        enterTransition = { enterTransition(width) }
    ) {
        val note = navHostController.previousBackStackEntry?.savedStateHandle?.get<Note>(
            key = Screens.NoteDetail.argumentKey
        )
        NoteDetailScreen(
            navHostController = navHostController,
            onBackPressedDispatcher = onBackPressedDispatcher,
            note = note
        )
    }
}