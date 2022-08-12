package br.com.mynotes.commom.compose.navigation

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import br.com.mynotes.commom.compose.animation.enterTransition
import br.com.mynotes.commom.compose.animation.exitTransition
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.presentation.screens.home.HomeScreen
import br.com.mynotes.features.notes.presentation.screens.note_detail.NoteDetailScreen
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.home(
    navHostController: NavHostController,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    with: Int
) {
    composable(
        route = Screens.Home.route,
        arguments = listOf(navArgument(Screens.Home.argumentKey) {
            type = NavType.BoolType
        }),
        popExitTransition = { exitTransition(-with) },
        enterTransition = { enterTransition(-with) }
    ) {
        var updateHome: Boolean? = false
        LaunchedEffect(LocalContext.current) {
            updateHome = it.arguments?.getBoolean(Screens.Home.argumentKey)
        }
        HomeScreen(
            navHostController = navHostController,
            onBackPressedDispatcher = onBackPressedDispatcher,
            updateHome = updateHome
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.noteDetail(
    navHostController: NavHostController,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    with: Int
) {
    composable(
        route = Screens.NoteDetail.route,
        popExitTransition = { exitTransition(with) },
        enterTransition = { enterTransition(with) }
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