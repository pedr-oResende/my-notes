package br.com.mynotes.features.notes.ui.screens.main.state

import br.com.mynotes.features.notes.ui.compose.navigation.Screens

enum class ScreenState(val route: String) {
    HomeScreen(route = Screens.Home.route),
    ArchiveScreen(route = Screens.Archive.route),
    TrashCanScreen(route = Screens.TrashCan.route);

    companion object {
        fun getScreenStateEnum(route: String?): ScreenState {
            return values().find { screen ->
                screen.route == route
            } ?: HomeScreen
        }
    }
}