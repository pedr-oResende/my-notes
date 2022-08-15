package br.com.mynotes.features.notes.presentation.screens.note_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mynotes.MyNotesApp
import br.com.mynotes.R
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
    private val _state = mutableStateOf(NoteDetailState())
    val state: State<NoteDetailState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val timeInNote = System.currentTimeMillis()

    fun onEvent(event: NoteDetailEvent) {
        when (event) {
            is NoteDetailEvent.ArchiveNote -> {
                viewModelScope.launch {
                    noteDetailUseCases.archiveNoteUseCase(getNote().copy(
                        isArchived = true
                    ))
                    _eventFlow.emit(UIEvents.ProcessNote)
                }
            }
            is NoteDetailEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteDetailUseCases.deleteNoteUseCase(getNote().id)
                    _eventFlow.emit(UIEvents.ProcessNote)
                }
            }
            is NoteDetailEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteDetailUseCases.addNoteUseCase(getNote())
                        _eventFlow.emit(UIEvents.ProcessNote)
                    } catch(e: InvalidNoteException) {
                        _eventFlow.emit(
                            UIEvents.ShowSnackBar(
                                message = e.message ?: MyNotesApp.getContext()?.getString(R.string.save_note_error_message) ?: ""
                            )
                        )
                    }
                }
            }
            is NoteDetailEvent.ToggleMarkPin -> {
                _state.value = state.value.copy(
                    isPinMarked = !state.value.isPinMarked
                )
            }
            is NoteDetailEvent.TitleChanged -> {
                _state.value = state.value.copy(
                    title = event.title
                )
            }
            is NoteDetailEvent.ContentChanged -> {
                _state.value = state.value.copy(
                    content = event.content
                )
            }
        }
    }

    fun loadNote(note: Note?) {
        _state.value = state.value.copy(
            note = note,
            title = note?.title ?: "",
            content = note?.content ?: "",
            isPinMarked = note?.isFixed ?: state.value.isPinMarked
        )
    }

    private fun getTimeStamp() = System.currentTimeMillis() - timeInNote

    private fun getCurrentDate(): String {
        val createdAt = state.value.note?.createAt ?: ""
        if (createdAt.isNotBlank())
            return createdAt
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun getNote(): Note = state.value.let { state ->
        Note(
            id = state.note?.id,
            title = state.title,
            content = state.content,
            isArchived = state.note?.isArchived ?: false,
            isFixed = state.isPinMarked,
            createAt = getCurrentDate(),
            timestamp = getTimeStamp()
        )
    }

}