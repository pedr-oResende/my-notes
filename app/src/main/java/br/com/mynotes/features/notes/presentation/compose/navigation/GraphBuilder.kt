
package br.com.mynotes.features.notes.presentation.compose.navigation

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.*
import androidx.navigation.compose.composable
import br.com.mynotes.features.notes.presentation.screens.main.MainNotesScreen
import br.com.mynotes.features.notes.presentation.screens.note_detail.NoteDetailScreen

fun NavGraphBuilder.home(
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState
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
            snackBarHostState = snackBarHostState
        )
    }
}

fun NavGraphBuilder.noteDetail(
    navHostController: NavHostController,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    snackBarHostState: SnackbarHostState
) {
    composable(
        route = Screens.NoteDetail.route
    ) {
        NoteDetailScreen(
            navHostController = navHostController,
            snackbarHostState = snackBarHostState,
            onBackPressedDispatcher = onBackPressedDispatcher
        )
    }
}