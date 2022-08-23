package br.com.mynotes.features.notes.presentation.screens.main

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import br.com.mynotes.commom.compose.navigation.Screens
import br.com.mynotes.commom.compose.navigation.home
import br.com.mynotes.commom.compose.navigation.noteDetail
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(onBackPressedDispatcher: OnBackPressedDispatcher) {
    val navHostController = rememberAnimatedNavController()
    val scaffoldState = rememberScaffoldState()
    AnimatedNavHost(
        navController = navHostController,
        startDestination = Screens.Home.route,
        builder = {
            home(
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
