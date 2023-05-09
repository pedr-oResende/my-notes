
package br.com.mynotes.features.notes.presentation.compose.navigation

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import br.com.mynotes.features.notes.presentation.screens.main.MainNotesScreen
import br.com.mynotes.features.notes.presentation.screens.note_detail.NoteDetailScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.home(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    composable(
        route = Screens.Home.route,
        arguments = listOf(navArgument(Screens.Home.argumentKey) {
            type = NavType.StringType
            nullable = true
        })
    ) {
        MainNotesScreen(
            navHostController = navHostController,
            snackbarHostState = snackbarHostState
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.noteDetail(
    navHostController: NavHostController,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    snackbarHostState: SnackbarHostState
) {
    composable(
        route = Screens.NoteDetail.route
    ) {
        NoteDetailScreen(
            navHostController = navHostController,
            snackbarHostState = snackbarHostState,
            onBackPressedDispatcher = onBackPressedDispatcher
        )
    }
}