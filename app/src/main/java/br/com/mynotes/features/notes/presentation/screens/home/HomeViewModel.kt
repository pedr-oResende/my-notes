package br.com.mynotes.features.notes.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mynotes.commom.util.PreferencesKey
import br.com.mynotes.commom.util.PreferencesWrapper
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.use_case.NoteUseCases
import br.com.mynotes.features.notes.presentation.util.NotesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val selectedNotes = mutableStateListOf<Int?>()
    private var getNotesJob: Job? = null

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state
//    private val _notesList = MutableStateFlow<StateUI<List<Note>>>(StateUI.Idle())
//    val notesList: StateFlow<StateUI<List<Note>>> = _notesList

    init {
        getNotes()
        _state.value = state.value.copy(
            isInGridMode = PreferencesWrapper.instance?.getBoolean(PreferencesKey.LAYOUT_STATE_KEY)
                ?: true
        )
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNotesUseCase(event.note)
                }
            }
            is NotesEvent.SelectNote -> {
                selectNote(event.noteId)
            }
            is NotesEvent.ToggleListView -> {
                toggleListView()
            }
            is NotesEvent.ToggleCloseSelection -> {
                disableSelectedMode()
            }
            is NotesEvent.AddNote -> {

            }
            is NotesEvent.ArchiveNote -> {

            }
            is NotesEvent.MarkNote -> {

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

    private fun selectNote(noteId: Int?) {
        if (selectedNotes.contains(noteId)) {
            selectedNotes.remove(noteId)
        } else {
            selectedNotes.add(noteId)
        }
        _state.value = state.value.copy(
            isInSelectedMode = selectedNotes.isNotEmpty()
        )
    }

    fun isNoteSelected(note: Note): Boolean = selectedNotes.contains(note.id)

    fun onItemLongClick(noteId: Int?) {
        onEvent(NotesEvent.SelectNote(noteId))
    }

    private fun toggleListView() {
        val value = !state.value.isInGridMode
        _state.value = state.value.copy(
            isInGridMode = value
        )
        PreferencesWrapper.instance?.putBoolean(
            key = PreferencesKey.LAYOUT_STATE_KEY,
            value = value
        )
    }

    private fun disableSelectedMode() {
        _state.value = state.value.copy(
            isInSelectedMode = false
        )
        selectedNotes.removeAll{ true }
    }

}