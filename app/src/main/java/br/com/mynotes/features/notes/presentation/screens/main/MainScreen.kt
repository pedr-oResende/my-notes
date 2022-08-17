package br.com.mynotes.features.notes.presentation.screens.main

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import br.com.mynotes.R
import br.com.mynotes.features.notes.presentation.compose.navigation.*
import br.com.mynotes.features.notes.presentation.compose.components.DrawerBody
import br.com.mynotes.features.notes.presentation.compose.components.DrawerHeader
import br.com.mynotes.features.notes.presentation.model.MenuItem
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(onBackPressedDispatcher: OnBackPressedDispatcher) {
    val navHostController = rememberAnimatedNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerShape = RectangleShape,
        drawerContent = {
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
                        scaffoldState.drawerState.close()
                        navHostController.navigate(item.route)
                    }

                },
                currentRoute = navHostController.currentDestination?.route ?: ""
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padding)
        ) {
            AnimatedNavHost(
                navController = navHostController,
                startDestination = Screens.Home.route,
                builder = {
                    home(
                        navHostController = navHostController,
                        scaffoldState = scaffoldState
                    )
                    archive(
                        navHostController = navHostController,
                        scaffoldState = scaffoldState
                    )
                    trashCan(
                        navHostController = navHostController,
                        scaffoldState = scaffoldState
                    )
                    noteDetail(
                        navHostController = navHostController,
                        onBackPressedDispatcher = onBackPressedDispatcher,
                        scaffoldState = scaffoldState
                    )
                }
            )
        }
    }
}