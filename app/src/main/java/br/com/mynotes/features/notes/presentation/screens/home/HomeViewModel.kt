package br.com.mynotes.features.notes.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import br.com.mynotes.commom.compose.navigation.Screens
import br.com.mynotes.commom.util.PreferencesKey
import br.com.mynotes.commom.util.PreferencesWrapper
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.use_case.NoteUseCases
import br.com.mynotes.features.notes.presentation.model.StateUI
import br.com.mynotes.features.notes.presentation.util.HomeEvent
import br.com.mynotes.features.notes.presentation.util.NotesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val selectedNotes = mutableStateListOf<Int?>()

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state
    private val _notesList = MutableStateFlow<StateUI<List<Note>>>(StateUI.Idle())
    val notesList: StateFlow<StateUI<List<Note>>> = _notesList
    private val _deleteNoteChannel = MutableStateFlow<StateUI<Long>>(StateUI.Idle())
    val deleteNoteChannel: StateFlow<StateUI<Long>> = _deleteNoteChannel
    private val _editNoteChannel = MutableStateFlow<StateUI<Long>>(StateUI.Idle())
    val editNoteChannel: StateFlow<StateUI<Long>> = _editNoteChannel

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
                deleteNotes()
            }
            is NotesEvent.SelectNote -> {
                selectNote(event.noteId)
            }
            is NotesEvent.ToggleListView -> {
                toggleListView()
            }
            is NotesEvent.ToggleCloseSelection -> {
                disableSelectedMode()
                if (event.cleanList)
                    cleanSelectedList()
            }
            is NotesEvent.ArchiveNote -> {
                editNotes()
            }
            is NotesEvent.RestoreNotes -> {
                editNotes()
            }
            is NotesEvent.PinNote -> {
                editNotes()
            }
            NotesEvent.ToggleMarkPin -> {
                _state.value = state.value.copy(
                    togglePin = !state.value.togglePin
                )
            }
            NotesEvent.OnNoteDeleted -> {
                onNoteDeleted()
            }
        }
    }

    private fun getNotes() {
        viewModelScope.launch {
            noteUseCases.getNotesUseCase().onStart {
                _notesList.emit(StateUI.Processing())
            }.catch {
                _notesList.emit(StateUI.Error(it.message.toString()))
            }.collect { data ->
                val notes = getNoteListFiltered(data)
                _notesList.emit(StateUI.Processed(notes))
            }
        }
    }

    private fun deleteNotes() {
        viewModelScope.launch {
            noteUseCases.deleteNotesUseCase(getSelectedList()).onStart {
                _deleteNoteChannel.emit(StateUI.Processing())
            }.catch {
                _deleteNoteChannel.emit(StateUI.Error(it.message.toString()))
            }.collect { data ->
                _deleteNoteChannel.emit(StateUI.Processed(data))
            }
        }
    }

    private fun editNotes() {
        viewModelScope.launch {
            noteUseCases.archiveNotesUseCase(getSelectedList()).onStart {
                _editNoteChannel.emit(StateUI.Processing())
            }.catch {
                _editNoteChannel.emit(StateUI.Error(it.message.toString()))
            }.collect { data ->
                _editNoteChannel.emit(StateUI.Processed(data))
            }
        }
    }

    fun refresh() {
        getNotes()
    }

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
    }

    fun cleanSelectedList() {
        selectedNotes.removeAll { true }
    }

    fun goToDetail(navHostController: NavHostController, note: Note) {
        Screens.NoteDetail.navigateWithArgument(
            navHostController = navHostController,
            argumentValue = note
        )
    }

    private fun getNoteListFiltered(notes: List<Note>): List<Note> {
        return notes.filter { note ->
            !note.isArchived
        }
    }

    private suspend fun getSelectedList(): List<Note> {
        val list =  mutableListOf<Note>()
        selectedNotes.forEach { id ->
            if(id != null){
                val note = noteUseCases.getNoteByIdUseCase(id)
                if (note != null) list.add(note)
            }
        }
        return list
    }

    fun resolveHomeEvent(homeEvent: HomeEvent) {
        when (homeEvent) {
            HomeEvent.NoteDeleted -> {
                refresh()
                onNoteDeleted()
            }
            HomeEvent.Refresh -> {
                refresh()
            }
        }
    }

    private fun onNoteDeleted() {
        _state.value = state.value.copy(
            aNoteHasBeenDeleted = true
        )
    }
}