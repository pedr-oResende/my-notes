package br.com.mynotes.features.notes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.mynotes.features.notes.presentation.compose.theme.MyNotesTheme
import br.com.mynotes.features.notes.presentation.screens.main.NavHostScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotesTheme {
                NavHostScreen(onBackPressedDispatcher = onBackPressedDispatcher)
            }
        }
    }
}