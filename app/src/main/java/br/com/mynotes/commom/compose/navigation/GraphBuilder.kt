package br.com.mynotes.commom.compose.navigation

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import br.com.mynotes.commom.extensions.getArgument
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.screens.home.HomeScreen
import br.com.mynotes.features.notes.presentation.screens.note_detail.NoteDetailScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.home(
    navHostController: NavHostController,
    scaffoldState: ScaffoldState
) {
    composable(
        route = Screens.Home.route,
        arguments = listOf(navArgument(Screens.Home.argumentKey) {
            type = NavType.StringType
            nullable = true
        })
    ) { backStackEntry ->
        val snackBarMessage =
            backStackEntry.arguments?.getString(Screens.Home.argumentKey) ?: ""
        HomeScreen(
            navHostController = navHostController,
            scaffoldState = scaffoldState,
            snackBarMessage = snackBarMessage
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