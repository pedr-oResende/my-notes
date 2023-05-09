package br.com.mynotes.features.notes.presentation.screens.note_detail

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.mynotes.R
import br.com.mynotes.commom.exceptions.InvalidNoteException
import br.com.mynotes.commom.extensions.getString
import br.com.mynotes.commom.extensions.ifNull
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.use_case.NoteDetailUseCases
import br.com.mynotes.features.notes.presentation.screens.note_detail.state.NoteDetailUI
import br.com.mynotes.features.notes.presentation.screens.note_detail.state.NoteDetailUIEvents
import br.com.mynotes.features.notes.presentation.screens.note_detail.state.NotesDetailActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDetailUseCases: NoteDetailUseCases,
    application: Application
) : AndroidViewModel(application) {
    private val _noteDetailUI = mutableStateOf(NoteDetailUI())
    val noteDetailUI: State<NoteDetailUI> = _noteDetailUI

    private val _eventFlow = MutableSharedFlow<NotesDetailActions>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val timeInNote = System.currentTimeMillis()

    fun onEvent(event: NoteDetailUIEvents) {
        when (event) {
            is NoteDetailUIEvents.ArchiveNote -> {
                viewModelScope.launch {
                    if (noteDetailUI.value.note?.isArchived == true)
                        noteDetailUseCases.unarchiveNoteUseCase(getNote())
                    else
                        noteDetailUseCases.archiveNoteUseCase(getNote())
                    _eventFlow.emit(NotesDetailActions.ProcessNote)
                }
            }
            is NoteDetailUIEvents.DeleteNote -> {
                viewModelScope.launch {
                    try {
                        noteDetailUseCases.moveToTrashCanUseCase(
                            note = getNote(),
                            context = getApplication<Application>().applicationContext
                        )
                        _eventFlow.emit(NotesDetailActions.ProcessNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(NotesDetailActions.DiscardNote)
                    }
                }
            }
            is NoteDetailUIEvents.SaveNote -> {
                viewModelScope.launch {
                    try {
                        val note =
                            if (noteDetailUI.value.isPinMarked && noteDetailUI.value.note?.isArchived == true)
                                getNote().copy(isArchived = false)
                            else
                                getNote()
                        noteDetailUseCases.addNoteUseCase(note)
                        _eventFlow.emit(NotesDetailActions.ProcessNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(NotesDetailActions.EmptyNote)
                    }
                }
            }
            is NoteDetailUIEvents.ToggleMarkPin -> {
                _noteDetailUI.value = noteDetailUI.value.copy(
                    isPinMarked = !noteDetailUI.value.isPinMarked
                )
            }
            is NoteDetailUIEvents.TitleChanged -> {
                _noteDetailUI.value = noteDetailUI.value.copy(
                    title = event.title
                )
            }
            is NoteDetailUIEvents.ContentChanged -> {
                _noteDetailUI.value = noteDetailUI.value.copy(
                    content = event.content
                )
            }
            is NoteDetailUIEvents.RestoreNote -> {
                viewModelScope.launch {
                    noteDetailUseCases.restoreNoteUseCase(getNote())
                    _eventFlow.emit(NotesDetailActions.ProcessNote)
                }
            }
            is NoteDetailUIEvents.TryToEditDeletedNote -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        NotesDetailActions.ShowRestoreNoteSnackBar(
                            text = getString(R.string.note_detail_disabled_text),
                            label = getString(R.string.label_restore)
                        )
                    )
                }
            }
        }
    }

    fun loadNote(note: Note?) {
        _noteDetailUI.value = noteDetailUI.value.copy(
            note = note,
            title = note?.title ifNull noteDetailUI.value.title,
            content = note?.content ifNull noteDetailUI.value.content,
            isPinMarked = note?.isFixed ifNull noteDetailUI.value.isPinMarked
        )
    }

    private fun getTimeStamp() = System.currentTimeMillis() - timeInNote

    private fun getCurrentDate(): String {
        val createdAt = noteDetailUI.value.note?.createAt ifNull ""
        if (createdAt.isNotBlank())
            return createdAt
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun getNote(): Note = noteDetailUI.value.let { state ->
        Note(
            id = state.note?.id,
            title = state.title,
            content = state.content,
            isFixed = state.isPinMarked,
            createAt = getCurrentDate(),
            timestamp = getTimeStamp(),
            isSelected = state.note?.isSelected ifNull false,
            isInTrashCan = state.note?.isInTrashCan ifNull false,
            isArchived = state.note?.isArchived ifNull false
        )
    }

}