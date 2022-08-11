package br.com.mynotes.commom.compose.navigation

import androidx.navigation.NavHostController

sealed class Screens(val route: String, val argumentKey: String) {

    fun navigate(navHostController: NavHostController) {
        navHostController.navigate(route)
    }

    fun <T> navigateWithArgument(
        navHostController: NavHostController,
        argumentValue: T?
    ) {
        navHostController.currentBackStackEntry?.savedStateHandle?.set(
            key = argumentKey,
            value = argumentValue
        )
        navigate(navHostController)
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

    object Home: Screens(
        route = "home",
        argumentKey = "home"
    ) {
        fun backToHome(navHostController: NavHostController, updateHome: Boolean = false) {
            navHostController.navigate(
                route = "kids_home/$updateHome"
            ) {
                popUpTo(0)
            }
        }
    }

}