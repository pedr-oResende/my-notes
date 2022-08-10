package br.com.mynotes.features.notes.presentation.screens.home

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.ui.theme.MyNotesTheme
import br.com.mynotes.features.notes.presentation.compose.widgets.TopBar

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    viewModel: HomeViewModel = hiltViewModel(),
    updateHome: Boolean?
) {
    MyNotesTheme {
        Scaffold(
            topBar = {
                TopBar(
                    title = "Your notes",
                    backPressedDispatcher = onBackPressedDispatcher,
                    hasNavigationIcon = false
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
            ) {

            }
        }
    }
}