package br.com.mynotes.features.notes.presentation.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import br.com.mynotes.commom.extensions.ifNull
import br.com.mynotes.commom.util.PreferencesWrapper
import br.com.mynotes.features.notes.presentation.compose.components.DefaultNavigationDrawer
import br.com.mynotes.features.notes.presentation.compose.navigation.Screens
import br.com.mynotes.features.notes.presentation.compose.theme.MyNotesTheme
import br.com.mynotes.features.notes.presentation.screens.main.components.MainNotes
import br.com.mynotes.features.notes.presentation.screens.main.components.MainTopBar
import br.com.mynotes.features.notes.presentation.screens.main.ui.DrawerScreens
import br.com.mynotes.features.notes.presentation.screens.main.ui.NoteListState
import kotlinx.coroutines.launch

@Composable
fun MainNotesScreen(
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val initialScreen = PreferencesWrapper.instance?.screenState ifNull DrawerScreens.Home
    val initialListState = PreferencesWrapper.instance?.listType ifNull NoteListState.Grid
    val screenState = remember { mutableStateOf(initialScreen) }
    val noteListState = remember { mutableStateOf(initialListState) }
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
                            PreferencesWrapper.instance?.screenState = item.screen
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
                        noteListState = noteListState
                    )
                },
                floatingActionButton = {
                    if (screenState.value == DrawerScreens.Home) {
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
                snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
            ) { paddingValues ->
                MainNotes(
                    modifier = Modifier.padding(paddingValues = paddingValues),
                    screenState = screenState,
                    navHostController = navHostController,
                    snackbarHostState = snackBarHostState,
                    noteListState = noteListState
                )
            }
        }
    }
}

