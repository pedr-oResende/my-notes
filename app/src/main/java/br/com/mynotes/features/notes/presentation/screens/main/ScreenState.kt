package br.com.mynotes.features.notes.presentation.screens.main

enum class ScreenState(val value: String) {
    HomeScreen(value = "home"),
    ArchiveScreen(value = "archive"),
    TrashCanScreen(value = "trash_can");

    companion object {
        fun getScreenStateEnum(value: String?): ScreenState {
            return values().find { gender ->
                gender.value == value
            } ?: HomeScreen
        }
    }
}