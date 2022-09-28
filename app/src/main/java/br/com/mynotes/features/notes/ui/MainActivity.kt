package br.com.mynotes.features.notes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import br.com.mynotes.features.notes.ui.compose.theme.MyNotesTheme
import br.com.mynotes.features.notes.ui.model.MenuItem
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotesTheme {
                MainScreen(onBackPressedDispatcher = onBackPressedDispatcher)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onBackPressedDispatcher: OnBackPressedDispatcher) {
    val navHostController = rememberAnimatedNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerStateHost = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerStateHost,
        gesturesEnabled = drawerStateHost.isOpen,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
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
                            drawerStateHost.close()
                            navHostController.navigate(item.route)
                        }
                    },
                    currentRoute = navHostController.currentDestination?.route
                )
            }
        }
    ) {
        AnimatedNavHost(
            navController = navHostController,
            startDestination = Screens.Home.route,
            builder = {
                home(
                    navHostController = navHostController,
                    snackbarHostState = snackbarHostState,
                    drawerStateHost = drawerStateHost
                )
                archive(
                    navHostController = navHostController,
                    snackbarHostState = snackbarHostState,
                    drawerStateHost = drawerStateHost
                )
                trashCan(
                    navHostController = navHostController,
                    drawerStateHost = drawerStateHost
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