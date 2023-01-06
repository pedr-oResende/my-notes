package br.com.mynotes.features.notes.ui.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import br.com.mynotes.R
import br.com.mynotes.commom.util.PreferencesWrapper
import br.com.mynotes.features.notes.ui.compose.components.DrawerBody
import br.com.mynotes.features.notes.ui.compose.components.DrawerHeader
import br.com.mynotes.features.notes.ui.compose.theme.MyNotesTheme
import br.com.mynotes.features.notes.ui.model.MenuItem
import br.com.mynotes.features.notes.ui.screens.archive.ArchiveListScreen
import br.com.mynotes.features.notes.ui.screens.home.HomeListScreen
import br.com.mynotes.features.notes.ui.screens.main.ui.DrawerScreens
import br.com.mynotes.features.notes.ui.screens.trash_can.TrashCanListScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNotesScreen(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val screenState: MutableState<DrawerScreens> = remember {
        mutableStateOf(PreferencesWrapper.instance?.getScreen() ?: DrawerScreens.Home)
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                DrawerBody(
                    items = listOf(
                        MenuItem(
                            screen = DrawerScreens.Home,
                            title = stringResource(R.string.menu_item_home),
                            icon = Icons.Outlined.Home
                        ),
                        MenuItem(
                            screen = DrawerScreens.Archive,
                            title = stringResource(R.string.menu_item_archive),
                            icon = Icons.Outlined.Archive
                        ),
                        MenuItem(
                            screen = DrawerScreens.TrashCan,
                            title = stringResource(R.string.menu_item_trash_can),
                            icon = Icons.Outlined.Delete
                        ),
                    ),
                    onItemClick = { item ->
                        scope.launch {
                            drawerState.close()
                            screenState.value = item.screen
                            PreferencesWrapper.instance?.setScreen(item.screen)
                        }
                    },
                    currentScreen = screenState.value
                )
            }
        }
    ) {
        MyNotesTheme {
            when (screenState.value) {
                DrawerScreens.Home -> {
                    HomeListScreen(
                        navHostController = navHostController,
                        snackbarHostState = snackbarHostState,
                        drawerState = drawerState
                    )
                }
                DrawerScreens.Archive -> {
                    ArchiveListScreen(
                        navHostController = navHostController,
                        snackbarHostState = snackbarHostState,
                        drawerState = drawerState
                    )
                }
                DrawerScreens.TrashCan -> {
                    TrashCanListScreen(
                        navHostController = navHostController,
                        drawerState = drawerState
                    )
                }
            }
        }
    }
}