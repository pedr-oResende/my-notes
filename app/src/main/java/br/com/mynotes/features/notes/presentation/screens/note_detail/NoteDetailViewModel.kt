package br.com.mynotes.features.notes.presentation.screens.note_detail

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.mynotes.R
import br.com.mynotes.commom.InvalidNoteException
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.use_case.NoteDetailUseCases
import br.com.mynotes.features.notes.presentation.util.NoteDetailUIEvents
import br.com.mynotes.features.notes.work_manager.DeleteNoteScheduler
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
    private val deleteNoteScheduler: DeleteNoteScheduler,
    application: Application
) : AndroidViewModel(application) {
    private val _noteDetailUI = mutableStateOf(NoteDetailUI())
    val noteDetailUI: State<NoteDetailUI> = _noteDetailUI

    private val _eventFlow = MutableSharedFlow<NotesDetailEvents>()
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
                    _eventFlow.emit(NotesDetailEvents.ProcessNote)
                }
            }
            is NoteDetailUIEvents.DeleteNote -> {
                viewModelScope.launch {
                    try {
                        val note = getNote()
                        noteDetailUseCases.moveToTrashCanUseCase(note)
                        deleteNoteScheduler.setupDeleteNoteWorker(
                            context = getApplication<Application>().applicationContext,
                            noteId = note.id
                        )
                        _eventFlow.emit(NotesDetailEvents.ProcessNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(NotesDetailEvents.DiscardNote)
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
                        _eventFlow.emit(NotesDetailEvents.ProcessNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(NotesDetailEvents.EmptyNote)
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
                    _eventFlow.emit(NotesDetailEvents.ProcessNote)
                }
            }
            is NoteDetailUIEvents.TryToEditDeletedNote -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        NotesDetailEvents.ShowRestoreNoteSnackBar(
                            text = getApplication<Application>().applicationContext.getString(R.string.note_detail_disabled_text),
                            label = getApplication<Application>().applicationContext.getString(R.string.label_restore)
                        )
                    )
                }
            }
        }
    }

    fun loadNote(note: Note?) {
        _noteDetailUI.value = noteDetailUI.value.copy(
            note = note,
            title = note?.title ?: noteDetailUI.value.title,
            content = note?.content ?: noteDetailUI.value.content,
            isPinMarked = note?.isFixed ?: noteDetailUI.value.isPinMarked
        )
    }

    private fun getTimeStamp() = System.currentTimeMillis() - timeInNote

    private fun getCurrentDate(): String {
        val createdAt = noteDetailUI.value.note?.createAt ?: ""
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
            isSelected = state.note?.isSelected ?: false,
            isDeleted = state.note?.isDeleted ?: false,
            isArchived = state.note?.isArchived ?: false
        )
    }

}