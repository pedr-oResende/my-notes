package br.com.mynotes.features.notes.ui.compose.navigation

import androidx.navigation.NavHostController
import br.com.mynotes.commom.extensions.putArgument

sealed class Screens(val route: String, val argumentKey: String) {

    fun <T> passArgument(argument: T?): String {
        return this.route.replace(oldValue = "{$argumentKey}", newValue = argument.toString())
    }

    fun backToHome(navHostController: NavHostController) {
        navHostController.navigate(route = Home.route) {
            popUpTo(0)
        }
    }

    fun <T> navigateWithArgument(
        navHostController: NavHostController,
        argumentValue: T?
    ) {
        navHostController.currentBackStackEntry?.savedStateHandle?.putArgument(
            key = argumentKey,
            argument = argumentValue
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
        navHostController.navigate(route)
    }


    object Home : Screens(
        route = "home?home_argument={home_argument}",
        argumentKey = "home_argument"
    )

    object NoteDetail : Screens(
        route = "note_detail",
        argumentKey = "note_detail_argument"
    )

}