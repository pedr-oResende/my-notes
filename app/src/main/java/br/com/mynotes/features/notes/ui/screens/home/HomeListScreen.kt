package br.com.mynotes.features.notes.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.mynotes.features.notes.ui.compose.navigation.Screens
import br.com.mynotes.features.notes.ui.screens.home.components.HomeList
import br.com.mynotes.features.notes.ui.screens.home.components.HomeTopBar
import br.com.mynotes.features.notes.ui.screens.home.ui.HomeEvents
import br.com.mynotes.features.notes.ui.screens.main.ui.SnackBarEvents
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeListScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    drawerState: DrawerState,
    isInGridMode: MutableState<Boolean>
) {
    LaunchedEffect(key1 = true) {
        val emptyNoteMessage = viewModel.getSnackBarMessage()
        if (emptyNoteMessage.isNotBlank()) {
            snackbarHostState.showSnackbar(
                message = emptyNoteMessage
            )
        }
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SnackBarEvents.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is SnackBarEvents.ShowUndoSnackBar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.label
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(HomeEvents.RestoreNotes)
                    }
                }
            }
        }
    }
    Scaffold(
        topBar = {
            HomeTopBar(
                viewModel = viewModel,
                drawerState = drawerState,
                isInGridMode = isInGridMode
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(Screens.NoteDetail.route)
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            HomeList(
                viewModel = viewModel,
                navHostController = navHostController,
                isInGridMode = isInGridMode.value
            )
        }
    }
}