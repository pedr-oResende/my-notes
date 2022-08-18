package br.com.mynotes.features.notes.presentation.compose.navigation

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.mynotes.commom.extensions.getArgument
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.screens.home.HomeScreen
import br.com.mynotes.features.notes.presentation.screens.note_detail.NoteDetailScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.home(
    navHostController: NavHostController,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    scaffoldState: ScaffoldState
) {
    composable(
        route = Screens.Home.route,
    ) {
        val snackBarMessage =
            navHostController.previousBackStackEntry?.savedStateHandle?.getArgument(
                key = Screens.Home.argumentKey
            ) ?: ""
        HomeScreen(
            navHostController = navHostController,
            scaffoldState = scaffoldState,
            snackBarMessage = snackBarMessage,
            onBackPressedDispatcher = onBackPressedDispatcher
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.noteDetail(
    navHostController: NavHostController,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    scaffoldState: ScaffoldState
) {
    composable(
        route = Screens.NoteDetail.route
    ) {
        val note = navHostController.previousBackStackEntry?.savedStateHandle?.getArgument<Note>(
            key = Screens.NoteDetail.argumentKey
        )
        NoteDetailScreen(
            navHostController = navHostController,
            onBackPressedDispatcher = onBackPressedDispatcher,
            note = note,
            scaffoldState = scaffoldState
        )
    }
}