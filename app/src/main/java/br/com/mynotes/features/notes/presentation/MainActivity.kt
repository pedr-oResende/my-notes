package br.com.mynotes.features.notes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.rememberScaffoldState
import br.com.mynotes.commom.compose.navigation.Screens
import br.com.mynotes.commom.compose.navigation.main
import br.com.mynotes.commom.compose.navigation.noteDetail
import br.com.mynotes.commom.util.PreferencesWrapper
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
                val navHostController = rememberAnimatedNavController()
                val scaffoldState = rememberScaffoldState()
                AnimatedNavHost(
                    navController = navHostController,
                    startDestination = Screens.Home.route,
                    builder = {
                        main(
                            navHostController = navHostController,
                            scaffoldState = scaffoldState
                        )
                        noteDetail(
                            navHostController = navHostController,
                            onBackPressedDispatcher = onBackPressedDispatcher,
                            scaffoldState = scaffoldState
                        )
                    }
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        PreferencesWrapper.instance?.clearPreferences()
    }
}