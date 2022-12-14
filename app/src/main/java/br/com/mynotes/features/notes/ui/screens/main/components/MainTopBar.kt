package br.com.mynotes.features.notes.ui.screens.main.components

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import br.com.mynotes.features.notes.ui.compose.animations.FadeTransition
import br.com.mynotes.features.notes.ui.screens.archive.components.ArchiveTopBar
import br.com.mynotes.features.notes.ui.screens.home.components.HomeTopBar
import br.com.mynotes.features.notes.ui.screens.main.ui.DrawerScreens
import br.com.mynotes.features.notes.ui.screens.trash_can.components.TrashCanTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    screen: DrawerScreens,
    drawerState: DrawerState,
    isInGridMode: MutableState<Boolean>
) {
    val scope = rememberCoroutineScope()
    val openDrawer: () -> Unit = {
        scope.launch { drawerState.open() }
    }
    FadeTransition(visible = screen is DrawerScreens.Home) {
        HomeTopBar(
            openDrawer = openDrawer,
            isInGridMode = isInGridMode
        )
    }
    FadeTransition(visible = screen is DrawerScreens.Archive) {
        ArchiveTopBar(
            openDrawer = openDrawer,
            isInGridMode = isInGridMode
        )
    }
    FadeTransition(visible = screen is DrawerScreens.TrashCan) {
        TrashCanTopBar(
            openDrawer = openDrawer
        )
    }
}