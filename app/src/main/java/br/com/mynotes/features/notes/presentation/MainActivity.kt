package br.com.mynotes.features.notes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.mynotes.commom.util.PreferencesWrapper
import br.com.mynotes.features.notes.presentation.screens.main.MainScreen
import br.com.mynotes.ui.theme.MyNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotesTheme {
                MainScreen(onBackPressedDispatcher)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        PreferencesWrapper.instance?.clearPreferences()
    }
}