package br.com.mynotes.features.notes.ui.screens.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.ui.compose.animations.FadeTransition
import br.com.mynotes.features.notes.ui.screens.archive.ArchiveNotesScreen
import br.com.mynotes.features.notes.ui.screens.home.HomeNotesScreen
import br.com.mynotes.features.notes.ui.screens.main.ui.DrawerScreens
import br.com.mynotes.features.notes.ui.screens.trash_can.TrashCanNotesScreen

@Composable
fun MainNotes(
    modifier: Modifier = Modifier,
    screenState: MutableState<DrawerScreens>,
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    isInGridMode: MutableState<Boolean>
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        FadeTransition(visible = screenState.value is DrawerScreens.Home) {
            HomeNotesScreen(
                navHostController = navHostController,
                snackbarHostState = snackbarHostState,
                isInGridMode = isInGridMode.value
            )
        }
        FadeTransition(visible = screenState.value is DrawerScreens.Archive) {
            ArchiveNotesScreen(
                navHostController = navHostController,
                snackbarHostState = snackbarHostState,
                isInGridMode = isInGridMode.value
            )
        }
        FadeTransition(visible = screenState.value is DrawerScreens.TrashCan) {
            TrashCanNotesScreen(
                navHostController = navHostController
            )
        }
    }
}