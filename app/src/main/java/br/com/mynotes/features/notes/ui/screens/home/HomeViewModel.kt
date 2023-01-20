package br.com.mynotes.features.notes.ui.screens.home

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.mynotes.R
import br.com.mynotes.commom.extensions.getString
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.use_case.wrapper.HomeUseCases
import br.com.mynotes.features.notes.ui.screens.home.ui.HomeEvents
import br.com.mynotes.features.notes.ui.screens.main.BaseViewModel
import br.com.mynotes.features.notes.ui.screens.main.ui.SnackBarEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    savedStateHandle: SavedStateHandle,
    application: Application
) : BaseViewModel(
    savedStateHandle,
    application
) {

    init {
        getHomeNotes()
    }

    fun onEvent(event: HomeEvents) {
        when (event) {
            is HomeEvents.ArchiveNotes -> {
                recentlyAffectedNotes.addAll(selectedNotes())
                archiveNotes(selectedNotes())
                disableSelectedMode()
            }
            is HomeEvents.MoveNoteToTrashCan -> {
                recentlyAffectedNotes.addAll(selectedNotes())
                moveToTrashCan(selectedNotes())
                disableSelectedMode()
            }
            is HomeEvents.RestoreNotes -> {
                restoreNotes(recentlyAffectedNotes)
                recentlyAffectedNotes.clear()
                disableSelectedMode()
            }
            is HomeEvents.ToggleMarkPin -> {
                markPinInNotes(selectedNotes())
                disableSelectedMode()
            }
        }
    }

    private fun getHomeNotes() {
        getNotesJob?.cancel()
        getNotesJob = homeUseCases.getMainNotesUseCase().onEach { notes ->
            setNotes(notes = notes)
        }.launchIn(viewModelScope)
    }

    private fun archiveNotes(notes: List<Note>) {
        viewModelScope.launch {
            notes.forEach { note ->
                homeUseCases.archiveNoteUseCase(note)
            }
            emitSnackBarEvent(
                SnackBarEvents.ShowUndoSnackBar(
                    message = getString(R.string.notes_list_notes_archived_message),
                    label = getString(R.string.label_undo)
                )
            )
        }
    }

    private fun moveToTrashCan(notes: List<Note>) {
        notes.forEach { note ->
            viewModelScope.launch {
                homeUseCases.moveToTrashCanUseCase(
                    note = note,
                    context = getApplication<Application>().applicationContext
                )
                emitSnackBarEvent(
                    SnackBarEvents.ShowUndoSnackBar(
                        message = getString(R.string.notes_list_notes_removed_message),
                        label = getString(R.string.label_undo)
                    )
                )
            }
        }
    }

    private fun restoreNotes(notes: List<Note>) {
        notes.forEach { note ->
            viewModelScope.launch {
                homeUseCases.restoreNoteUseCase(note)
            }
        }
    }

    private fun markPinInNotes(notes: List<Note>) {
        notes.forEach { note ->
            viewModelScope.launch {
                homeUseCases.markPinUseCase(note = note, isFixed = !notes.all { it.isFixed })
            }
        }
    }

    fun archiveOnSwipe(note: Note) {
        recentlyAffectedNotes.clear()
        recentlyAffectedNotes.add(note)
        archiveNotes(listOf(note))
    }
}