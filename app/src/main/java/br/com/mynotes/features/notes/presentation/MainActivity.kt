package br.com.mynotes.features.notes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import br.com.mynotes.features.notes.presentation.compose.navigation.*
import br.com.mynotes.features.notes.presentation.compose.widgets.DrawerBody
import br.com.mynotes.features.notes.presentation.compose.widgets.DrawerHeader
import br.com.mynotes.features.notes.presentation.model.MenuItem
import br.com.mynotes.ui.theme.MyNotesTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotesTheme {
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
                                    title = "Home",
                                    icon = Icons.Outlined.Home
                                ),
                                MenuItem(
                                    route = Screens.Archive.route,
                                    title = "Archive",
                                    icon = Icons.Outlined.Archive
                                ),
                                MenuItem(
                                    route = Screens.TrashCan.route,
                                    title = "Recently deleted",
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
        }
    }
}