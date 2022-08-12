package br.com.mynotes.features.notes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import br.com.mynotes.commom.compose.navigation.Screens
import br.com.mynotes.commom.compose.navigation.home
import br.com.mynotes.commom.compose.navigation.noteDetail
import br.com.mynotes.ui.theme.MyNotesTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotesTheme {
                val navController = rememberAnimatedNavController()
                BoxWithConstraints {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screens.Home.route,
                        builder = {
                            home(
                                navHostController = navController,
                                width = constraints.maxWidth
                            )
                            noteDetail(
                                navHostController = navController,
                                onBackPressedDispatcher = onBackPressedDispatcher,
                                width = constraints.maxWidth
                            )
                        }

                    )
                }
            }
        }
    }
}