package br.com.mynotes.features.notes.presentation.compose.navigation

import androidx.navigation.NavHostController

sealed class Screens(val route: String, val argumentKey: String) {

    fun navigateUp(navHostController: NavHostController) {
        navHostController.navigateUp()
    }

    fun navigate(navHostController: NavHostController) {
        navigateWithArgument(navHostController = navHostController, argumentValue = null)
    }

    fun <T> navigateWithArgument(
        navHostController: NavHostController,
        argumentValue: T?
    ) {
        navHostController.currentBackStackEntry?.savedStateHandle?.set(
            key = argumentKey,
            value = argumentValue
        )
        navHostController.navigate(route)
    }

    fun <T> navigateWithListArgument(
        navHostController: NavHostController,
        argumentValue: List<T>?
    ) {
        navHostController.currentBackStackEntry?.savedStateHandle?.set(
            key = argumentKey,
            value = argumentValue
        )
        navigate(navHostController)
    }

    object Home : Screens(
        route = "home",
        argumentKey = "home_argument"
    )

    object NoteDetail : Screens(
        route = "note_detail",
        argumentKey = "note_detail_argument"
    )

}