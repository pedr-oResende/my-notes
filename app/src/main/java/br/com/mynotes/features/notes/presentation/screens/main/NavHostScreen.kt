package br.com.mynotes.features.notes.presentation.screens.main

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.mynotes.features.notes.presentation.compose.navigation.*

@Composable
fun NavHostScreen(onBackPressedDispatcher: OnBackPressedDispatcher) {
    val navHostController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navHostController,
        startDestination = Screens.Home.route,
        builder = {
            home(
                navHostController = navHostController,
                snackBarHostState = snackBarHostState
            )
            noteDetail(
                navHostController = navHostController,
                onBackPressedDispatcher = onBackPressedDispatcher,
                snackBarHostState = snackBarHostState
            )
        }
    )
}