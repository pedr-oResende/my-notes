@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)

package br.com.mynotes.features.notes.ui.compose.navigation

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import br.com.mynotes.features.notes.ui.screens.main.MainNoteListScreen
import br.com.mynotes.features.notes.ui.screens.note_detail.NoteDetailScreen
import com.google.accompanist.navigation.animation.composable

fun NavGraphBuilder.main(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    drawerStateHost: DrawerState
) {
    composable(
        route = Screens.Home.route,
        arguments = listOf(navArgument(Screens.Home.argumentKey) {
            type = NavType.StringType
            nullable = true
        })
    ) {
        MainNoteListScreen(
            navHostController = navHostController,
            snackbarHostState = snackbarHostState,
            drawerStateHost = drawerStateHost
        )
    }
}

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