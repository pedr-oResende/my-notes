package br.com.mynotes.features.notes.presentation.screens.note_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mynotes.commom.InvalidNoteException
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.use_case.NoteDetailUseCases
import br.com.mynotes.features.notes.presentation.util.NoteDetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDetailUseCases: NoteDetailUseCases
) : ViewModel() {
    private val _noteDetailUI = mutableStateOf(NoteDetailUI())
    val noteDetailUI: State<NoteDetailUI> = _noteDetailUI

    private val _eventFlow = MutableSharedFlow<NotesDetailEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val timeInNote = System.currentTimeMillis()

    fun onEvent(event: NoteDetailEvent) {
        when (event) {
            is NoteDetailEvent.ArchiveNote -> {
                viewModelScope.launch {
                    noteDetailUseCases.archiveNoteUseCase(getNote())
                    _eventFlow.emit(NotesDetailEvents.ProcessNote)
                }
            }
            is NoteDetailEvent.DeleteNote -> {
                viewModelScope.launch {
                    try {
                        noteDetailUseCases.moveToTrashCanUseCase(getNote())
                        _eventFlow.emit(NotesDetailEvents.ProcessNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(NotesDetailEvents.DiscardNote)
                    }
                }
            }
            is NoteDetailEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteDetailUseCases.addNoteUseCase(getNote())
                        _eventFlow.emit(NotesDetailEvents.ProcessNote)
                    } catch(e: InvalidNoteException) {
                        _eventFlow.emit(NotesDetailEvents.EmptyNote)
                    }
                }
            }
            is NoteDetailEvent.ToggleMarkPin -> {
                _noteDetailUI.value = noteDetailUI.value.copy(
                    isPinMarked = !noteDetailUI.value.isPinMarked
                )
            }
            is NoteDetailEvent.TitleChanged -> {
                _noteDetailUI.value = noteDetailUI.value.copy(
                    title = event.title
                )
            }
            is NoteDetailEvent.ContentChanged -> {
                _noteDetailUI.value = noteDetailUI.value.copy(
                    content = event.content
                )
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
            isArchived = state.note?.isArchived ?: false,
            isFixed = state.isPinMarked,
            createAt = getCurrentDate(),
            timestamp = getTimeStamp(),
            isSelected = false,
            isDeleted = false
        )
    }

}