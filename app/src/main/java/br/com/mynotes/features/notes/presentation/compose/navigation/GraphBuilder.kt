package br.com.mynotes.features.notes.presentation.compose.navigation

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import br.com.mynotes.features.notes.presentation.compose.animation.exitTransition
import br.com.mynotes.features.notes.presentation.compose.animation.popEnterTransition
import br.com.mynotes.features.notes.presentation.screens.home.HomeScreen
import br.com.mynotes.features.notes.presentation.screens.home.HomeViewModel
import org.koin.androidx.compose.getViewModel

@ExperimentalAnimationApi
fun NavGraphBuilder.home(
    navHostController: NavHostController,
    onBackPressedDispatcher: OnBackPressedDispatcher
) {
    composable(
        route = Screens.Home.route,
        arguments = listOf(navArgument(Screens.Home.argumentKey) {
            type = NavType.BoolType
        }),
        exitTransition = { exitTransition },
        popEnterTransition = { popEnterTransition }
    ) {
        val viewModel = getViewModel<HomeViewModel>()
        var updateHome: Boolean? = false
        LaunchedEffect(LocalContext.current) {
            updateHome = it.arguments?.getBoolean(Screens.Home.argumentKey)
        }
        HomeScreen(
            navHostController = navHostController,
            onBackPressedDispatcher = onBackPressedDispatcher,
            viewModel = viewModel,
            updateHome = updateHome
        )
    }
}