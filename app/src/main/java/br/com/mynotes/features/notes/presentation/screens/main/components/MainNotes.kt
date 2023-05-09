package br.com.mynotes.features.notes.presentation.screens.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.presentation.compose.animations.FadeTransition
import br.com.mynotes.features.notes.presentation.screens.archive.ArchiveNotesScreen
import br.com.mynotes.features.notes.presentation.screens.home.HomeNotesScreen
import br.com.mynotes.features.notes.presentation.screens.main.ui.DrawerScreens
import br.com.mynotes.features.notes.presentation.screens.main.ui.NoteListState
import br.com.mynotes.features.notes.presentation.screens.trash_can.TrashCanNotesScreen

@Composable
fun MainNotes(
    modifier: Modifier = Modifier,
    screenState: MutableState<DrawerScreens>,
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    noteListState: MutableState<NoteListState>
) {
    Box(modifier = modifier.fillMaxSize()) {
        FadeTransition(visible = screenState.value == DrawerScreens.Home) {
            HomeNotesScreen(
                navHostController = navHostController,
                snackbarHostState = snackbarHostState,
                noteListState = noteListState.value
            )
        }
        FadeTransition(visible = screenState.value == DrawerScreens.Archive) {
            ArchiveNotesScreen(
                navHostController = navHostController,
                snackBarHostState = snackbarHostState,
                noteListState = noteListState.value
            )
        }
        FadeTransition(visible = screenState.value == DrawerScreens.TrashCan) {
            TrashCanNotesScreen(
                navHostController = navHostController
            )
        }
    }
}