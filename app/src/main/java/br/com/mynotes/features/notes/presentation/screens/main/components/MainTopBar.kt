package br.com.mynotes.features.notes.presentation.screens.main.components

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.*
import br.com.mynotes.features.notes.presentation.compose.animations.FadeTransition
import br.com.mynotes.features.notes.presentation.screens.archive.components.ArchiveTopBar
import br.com.mynotes.features.notes.presentation.screens.home.components.HomeTopBar
import br.com.mynotes.features.notes.presentation.screens.main.ui.DrawerScreens
import br.com.mynotes.features.notes.presentation.screens.main.ui.NoteListState
import br.com.mynotes.features.notes.presentation.screens.trash_can.components.TrashCanTopBar
import kotlinx.coroutines.launch

@Composable
fun MainTopBar(
    screen: DrawerScreens,
    drawerState: DrawerState,
    noteListState: MutableState<NoteListState>
) {
    val scope = rememberCoroutineScope()
    val openDrawer: () -> Unit = {
        scope.launch { drawerState.open() }
    }
    FadeTransition(visible = screen == DrawerScreens.Home) {
        HomeTopBar(
            openDrawer = openDrawer,
            noteListState = noteListState
        )
    }
    FadeTransition(visible = screen == DrawerScreens.Archive) {
        ArchiveTopBar(
            openDrawer = openDrawer,
            noteListState = noteListState
        )
    }
    FadeTransition(visible = screen == DrawerScreens.TrashCan) {
        TrashCanTopBar(
            openDrawer = openDrawer
        )
    }
}