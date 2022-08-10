package br.com.mynotes.features.notes.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mynotes.features.notes.domain.use_case.NoteUseCases
import br.com.mynotes.features.notes.presentation.util.NotesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        getNotes()
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
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

    private fun getNotes() {
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
        getNotesJob = noteUseCases.getNotesUseCase().onEach { notes ->
            _state.value = state.value.copy(
                notes = notes
            )
        }.launchIn(viewModelScope)
    }

    fun refresh() = getNotes()

}