package br.com.mynotes.features.notes.ui.compose.navigation

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import br.com.mynotes.features.notes.ui.screens.main.MainNoteListScreen
import br.com.mynotes.features.notes.ui.screens.note_detail.NoteDetailScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.main(
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
        MainNoteListScreen(
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
        NoteDetailScreen(
            navHostController = navHostController,
            onBackPressedDispatcher = onBackPressedDispatcher,
            scaffoldState = scaffoldState
        )
    }
}