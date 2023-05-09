package br.com.mynotes.features.notes.presentation.screens.archive

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.mynotes.R
import br.com.mynotes.commom.extensions.getString
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.use_case.wrapper.ArchiveUseCases
import br.com.mynotes.features.notes.presentation.screens.archive.ui.ArchiveEvents
import br.com.mynotes.features.notes.presentation.screens.main.BaseViewModel
import br.com.mynotes.features.notes.presentation.screens.main.ui.SnackBarEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    private val archiveUseCases: ArchiveUseCases,
    savedStateHandle: SavedStateHandle,
    application: Application
) : BaseViewModel(
    savedStateHandle,
    application
) {

    init {
        getArchivedNotes()
    }

    fun onEvent(event: ArchiveEvents) {
        when (event) {
            ArchiveEvents.UnArchiveNote -> {
                unArchiveNotes(selectedNotes())
                disableSelectedMode()
            }
            is ArchiveEvents.MoveNoteToTrashCan -> {
                recentlyAffectedNotes.addAll(selectedNotes())
                moveToTrashCan(selectedNotes().map { note ->
                    note.copy(
                        isInTrashCan = true,
                        isArchived = false
                    )
                })
                disableSelectedMode()
            }
            is ArchiveEvents.RestoreNotes -> {
                restoreNotes(recentlyAffectedNotes)
                recentlyAffectedNotes.removeAll { true }
                disableSelectedMode()
            }
            is ArchiveEvents.ToggleMarkPin -> {
                markPinInNotes(selectedNotes())
                disableSelectedMode()
            }
        }
    }

    private fun getArchivedNotes() {
        getNotesJob?.cancel()
        getNotesJob = archiveUseCases.getArchivedNotesUseCase().onEach { notes ->
            setNotes(notes = notes)
        }.launchIn(viewModelScope)
    }

    private fun unArchiveNotes(notes: List<Note>) {
        notes.forEach { note ->
            viewModelScope.launch {
                archiveUseCases.unarchiveNoteUseCase(note)
            }
        }
    }

    private fun moveToTrashCan(notes: List<Note>) {
        notes.forEach { note ->
            viewModelScope.launch {
                archiveUseCases.moveToTrashCanUseCase(
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
                archiveUseCases.restoreNoteUseCase(note)
            }
        }
    }

    private fun markPinInNotes(notes: List<Note>) {
        notes.forEach { note ->
            viewModelScope.launch {
                archiveUseCases.markPinUseCase(note = note, isFixed = !notes.all { it.isFixed })
            }
        }
    }
}