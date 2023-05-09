package br.com.mynotes.features.notes.presentation.compose.navigation

sealed class Screens(route: String, argumentKey: String) : ScreenNavOperations(route, argumentKey) {

    object Home : Screens(
        route = "home?home_argument={home_argument}",
        argumentKey = "home_argument"
    )

    object NoteDetail : Screens(
        route = "note_detail",
        argumentKey = "note_detail_argument"
    )

}