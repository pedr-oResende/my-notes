package br.com.mynotes.commom.compose.navigation

import androidx.navigation.NavHostController

sealed class Screens(val route: String, val argumentKey: String) {

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
        argumentKey = "home"
    )

    object NoteDetail : Screens(
        route = "note_detail",
        argumentKey = "note_detail"
    )

}