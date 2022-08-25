package br.com.mynotes.features.notes.presentation.screens.main.state

enum class ScreenState(val value: String) {
    HomeScreen(value = "home"),
    ArchiveScreen(value = "archive"),
    TrashCanScreen(value = "trash_can");

    companion object {
        fun getScreenStateEnum(value: String?): ScreenState {
            return values().find { screen ->
                screen.value == value
            } ?: HomeScreen
        }
    }
}