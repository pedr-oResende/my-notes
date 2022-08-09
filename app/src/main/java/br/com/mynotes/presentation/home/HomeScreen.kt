package br.com.mynotes.presentation.home

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import br.com.mynotes.compose.ui.theme.MyNotesTheme
import br.com.mynotes.compose.widgets.TopBar

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    viewModel: HomeViewModel,
    updateHome: Boolean?
) {
    MyNotesTheme {
        Scaffold(
            topBar = {
                TopBar(
                    title = "Home",
                    backPressedDispatcher = onBackPressedDispatcher,
                    hasNavigationIcon = false
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Hello world!")
            }
        }
    }
}