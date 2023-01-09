package br.com.mynotes.features.notes.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import br.com.mynotes.commom.extensions.ifNull
import br.com.mynotes.commom.util.PreferencesKey
import br.com.mynotes.commom.util.PreferencesWrapper
import br.com.mynotes.features.notes.ui.compose.components.DefaultNavigationDrawer
import br.com.mynotes.features.notes.ui.compose.navigation.Screens
import br.com.mynotes.features.notes.ui.compose.theme.MyNotesTheme
import br.com.mynotes.features.notes.ui.screens.main.components.MainNotes
import br.com.mynotes.features.notes.ui.screens.main.components.MainTopBar
import br.com.mynotes.features.notes.ui.screens.main.ui.DrawerScreens
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
        mutableStateOf(PreferencesWrapper.instance?.getScreen() ifNull DrawerScreens.Home)
    }
    val isInGridMode = remember {
        mutableStateOf(
            value = PreferencesWrapper.instance?.getBoolean(
                key = PreferencesKey.NOTE_LIST_TYPE_STATE_KEY
            ) ifNull true
        )
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet {
                DefaultNavigationDrawer(
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
            Scaffold(
                topBar = {
                    MainTopBar(
                        screen = screenState.value,
                        drawerState = drawerState,
                        isInGridMode = isInGridMode
                    )
                },
                floatingActionButton = {
                    if (screenState.value is DrawerScreens.Home) {
                        FloatingActionButton(
                            onClick = {
                                navHostController.navigate(Screens.NoteDetail.route)
                            },
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.secondary
                        ) {
                            Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
                        }
                    }
                },
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
            ) { paddingValues ->
                MainNotes(
                    modifier = Modifier.padding(paddingValues = paddingValues),
                    screenState = screenState,
                    navHostController = navHostController,
                    snackbarHostState = snackbarHostState,
                    isInGridMode =  isInGridMode
                )
            }
        }
    }
}

