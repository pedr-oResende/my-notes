
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
import br.com.mynotes.features.notes.ui.screens.archive.ArchiveListScreen
import br.com.mynotes.features.notes.ui.screens.home.HomeListScreen
import br.com.mynotes.features.notes.ui.screens.note_detail.NoteDetailScreen
import br.com.mynotes.features.notes.ui.screens.trash_can.TrashCanListScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
fun NavGraphBuilder.home(
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
        HomeListScreen(
            navHostController = navHostController,
            snackbarHostState = snackbarHostState,
            drawerStateHost = drawerStateHost
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
fun NavGraphBuilder.archive(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    drawerStateHost: DrawerState
) {
    composable(
        route = Screens.Archive.route,
        arguments = listOf(navArgument(Screens.Archive.argumentKey) {
            type = NavType.StringType
            nullable = true
        })
    ) {
        ArchiveListScreen(
            navHostController = navHostController,
            snackbarHostState = snackbarHostState,
            drawerStateHost = drawerStateHost
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
fun NavGraphBuilder.trashCan(
    navHostController: NavHostController,
    drawerStateHost: DrawerState
) {
    composable(
        route = Screens.TrashCan.route,
        arguments = listOf(navArgument(Screens.TrashCan.argumentKey) {
            type = NavType.StringType
            nullable = true
        })
    ) {
        TrashCanListScreen(
            navHostController = navHostController,
            drawerStateHost = drawerStateHost
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