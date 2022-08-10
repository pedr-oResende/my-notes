package br.com.mynotes.features.notes.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mynotes.features.notes.domain.use_case.NoteUseCases
import br.com.mynotes.features.notes.presentation.util.NoteOrder
import br.com.mynotes.features.notes.presentation.util.NotesEvent
import br.com.mynotes.features.notes.presentation.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private var getNotesJob: Job? = null

    private val _state = MutableStateFlow(NotesState())
    val state: MutableStateFlow<NotesState> = _state
//    private val _notesList = MutableStateFlow<StateUI<List<Note>>>(StateUI.Idle())
//    val notesList: StateFlow<StateUI<List<Note>>> = _notesList

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class != event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNotesUseCase(event.note)
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            is NotesEvent.ToggleListView -> {
                _state.value = state.value.copy(
                    isInGridMode = !state.value.isInGridMode
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
//        viewModelScope.launch {
//            noteUseCases.getNotesUseCase(noteOrder).onStart {
//                _notesList.emit(StateUI.Processing())
//            }.catch {
//                _notesList.emit(StateUI.Error(it.message.toString()))
//            }.collect { data ->
//                _notesList.emit(StateUI.Processed(data))
//            }
//        }
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesUseCase(noteOrder).onEach { notes ->
            _state.value = state.value.copy(
                notes = notes,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }

}