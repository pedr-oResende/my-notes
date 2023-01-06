package br.com.mynotes.features.notes.ui.screens.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import br.com.mynotes.commom.util.PreferencesKey
import br.com.mynotes.commom.util.PreferencesWrapper
import br.com.mynotes.features.notes.ui.screens.archive.ArchiveNotesScreen
import br.com.mynotes.features.notes.ui.screens.home.HomeNotesScreen
import br.com.mynotes.features.notes.ui.screens.main.ui.DrawerScreens
import br.com.mynotes.features.notes.ui.screens.trash_can.TrashCanNotesScreen

@Composable
fun MainNotes(
    modifier: Modifier = Modifier,
    screenState: MutableState<DrawerScreens>,
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        val isInGridMode = remember {
            mutableStateOf(
                PreferencesWrapper.instance?.getBoolean(
                    key = PreferencesKey.NOTE_LIST_TYPE_STATE_KEY
                ) ?: true
            )
        }
        when (screenState.value) {
            DrawerScreens.Home -> {
                HomeNotesScreen(
                    navHostController = navHostController,
                    snackbarHostState = snackbarHostState,
                    isInGridMode = isInGridMode.value
                )
            }
            DrawerScreens.Archive -> {
                ArchiveNotesScreen(
                    navHostController = navHostController,
                    snackbarHostState = snackbarHostState,
                    isInGridMode = isInGridMode.value
                )
            }
            DrawerScreens.TrashCan -> {
                TrashCanNotesScreen(
                    navHostController = navHostController
                )
            }
        }
    }
}