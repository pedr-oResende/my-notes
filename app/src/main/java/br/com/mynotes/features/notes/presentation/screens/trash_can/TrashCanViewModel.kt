package br.com.mynotes.features.notes.presentation.screens.trash_can

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.mynotes.commom.extensions.ifNull
import br.com.mynotes.commom.util.PreferencesKey
import br.com.mynotes.commom.util.PreferencesWrapper
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.use_case.wrapper.TrashCanUseCases
import br.com.mynotes.features.notes.presentation.screens.main.BaseViewModel
import br.com.mynotes.features.notes.presentation.screens.trash_can.ui.TrashCanEvents
import br.com.mynotes.features.notes.presentation.screens.trash_can.ui.TrashCanUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrashCanViewModel @Inject constructor(
    private val trashCanUseCases: TrashCanUseCases,
    savedStateHandle: SavedStateHandle,
    application: Application
) : BaseViewModel(
    savedStateHandle,
    application
) {

    private val _trashCanUI = mutableStateOf(TrashCanUI())
    val trashCanUI: State<TrashCanUI> = _trashCanUI

    init {
        _trashCanUI.value = trashCanUI.value.copy(
            showAutoDeleteMessage = PreferencesWrapper.instance?.getBoolean(
                key = PreferencesKey.SHOW_AUTO_DELETE_MESSAGE_KEY
            ) ifNull true
        )
        getTrashCanNotes()
    }

    fun onEvent(event: TrashCanEvents) {
        when (event) {
            is TrashCanEvents.DeleteNotes -> {
                deleteNotes(selectedNotes())
                disableSelectedMode()
            }
            is TrashCanEvents.ClearTrashCan -> {
                deleteNotes(getNotes())
            }
            is TrashCanEvents.CloseAutoDeleteMessage -> {
                _trashCanUI.value = trashCanUI.value.copy(
                    showAutoDeleteMessage = false
                )
                PreferencesWrapper.instance?.putBoolean(
                    key = PreferencesKey.SHOW_AUTO_DELETE_MESSAGE_KEY,
                    value = false
                )
            }
            is TrashCanEvents.RestoreFromTrashCan -> {
                restoreNotes(selectedNotes())
                disableSelectedMode()
            }
        }
    }

    private fun getTrashCanNotes() {
        getNotesJob?.cancel()
        getNotesJob = trashCanUseCases.getDeletedNotesUseCase().onEach { notes ->
            setNotes(notes = notes)
        }.launchIn(viewModelScope)
    }

    private fun deleteNotes(notes: List<Note>) {
        notes.forEach { note ->
            viewModelScope.launch {
                trashCanUseCases.deleteNoteUseCase(note.id)
            }
        }
    }

    private fun restoreNotes(notes: List<Note>) {
        notes.forEach { note ->
            viewModelScope.launch {
                trashCanUseCases.restoreNoteUseCase(note)
            }
        }
    }
}