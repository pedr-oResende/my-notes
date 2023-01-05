package br.com.mynotes.features.notes.ui.screens.main

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.mynotes.R
import br.com.mynotes.features.notes.ui.compose.components.DrawerBody
import br.com.mynotes.features.notes.ui.compose.components.DrawerHeader
import br.com.mynotes.features.notes.ui.compose.navigation.*
import br.com.mynotes.features.notes.ui.model.MenuItem
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onBackPressedDispatcher: OnBackPressedDispatcher) {
    val navHostController = rememberAnimatedNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                DrawerBody(
                    items = listOf(
                        MenuItem(
                            route = Screens.Home.route,
                            title = stringResource(R.string.menu_item_home),
                            icon = Icons.Outlined.Home
                        ),
                        MenuItem(
                            route = Screens.Archive.route,
                            title = stringResource(R.string.menu_item_archive),
                            icon = Icons.Outlined.Archive
                        ),
                        MenuItem(
                            route = Screens.TrashCan.route,
                            title = stringResource(R.string.menu_item_trash_can),
                            icon = Icons.Outlined.Delete
                        ),
                    ),
                    onItemClick = { item ->
                        scope.launch {
                            drawerState.close()
                            navHostController.navigate(item.route)
                        }
                    },
                    currentRoute = navHostController.currentDestination?.route
                )
            }
        }
    ) {
        AnimatedNavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navHostController,
            startDestination = Screens.Home.route,
            builder = {
                home(
                    navHostController = navHostController,
                    snackbarHostState = snackbarHostState,
                    drawerStateHost = drawerState
                )
                archive(
                    navHostController = navHostController,
                    snackbarHostState = snackbarHostState,
                    drawerStateHost = drawerState
                )
                trashCan(
                    navHostController = navHostController,
                    drawerStateHost = drawerState
                )
                noteDetail(
                    navHostController = navHostController,
                    onBackPressedDispatcher = onBackPressedDispatcher,
                    snackbarHostState = snackbarHostState
                )
            }
        )
    }
}