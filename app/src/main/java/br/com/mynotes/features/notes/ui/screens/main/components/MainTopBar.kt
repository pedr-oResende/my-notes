package br.com.mynotes.features.notes.ui.screens.main.components

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import br.com.mynotes.commom.util.PreferencesKey
import br.com.mynotes.commom.util.PreferencesWrapper
import br.com.mynotes.features.notes.ui.screens.archive.components.ArchiveTopBar
import br.com.mynotes.features.notes.ui.screens.home.components.HomeTopBar
import br.com.mynotes.features.notes.ui.screens.main.ui.DrawerScreens
import br.com.mynotes.features.notes.ui.screens.trash_can.components.TrashCanTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    screen: DrawerScreens,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    val openDrawer: () -> Unit = {
        scope.launch { drawerState.open() }
    }
    val isInGridMode = remember {
        mutableStateOf(
            value = PreferencesWrapper.instance?.getBoolean(
                key = PreferencesKey.NOTE_LIST_TYPE_STATE_KEY
            ) ?: true
        )
    }
    when (screen) {
        DrawerScreens.Archive -> {
            ArchiveTopBar(
                openDrawer = openDrawer,
                isInGridMode = isInGridMode
            )
        }
        DrawerScreens.Home -> {
            HomeTopBar(
                openDrawer = openDrawer,
                isInGridMode = isInGridMode
            )
        }
        DrawerScreens.TrashCan -> {
            TrashCanTopBar(
                openDrawer = openDrawer
            )
        }
    }
}