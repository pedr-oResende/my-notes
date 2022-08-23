package br.com.mynotes.features.notes.presentation.compose.navigation

import androidx.navigation.NavHostController

sealed class Screens(val route: String, val argumentKey: String) {

    fun <T> passArgument(message: T?): String {
        return this.route.replace(oldValue = "{$argumentKey}", newValue = message.toString())
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
        route = "home/{home_argument}",
        argumentKey = "home_argument"
    )

    object NoteDetail : Screens(
        route = "note_detail/{note_detail_argument}",
        argumentKey = "note_detail_argument"
    )

}