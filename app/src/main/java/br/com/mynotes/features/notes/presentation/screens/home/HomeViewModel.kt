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
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesUseCase().onEach { notes ->
            _state.value = state.value.copy(
                notes = getNoteListFiltered(notes)
            )
        }.launchIn(viewModelScope)
    }

    private fun deleteNotes() {
        viewModelScope.launch {
            noteUseCases.deleteNotesUseCase(selectedNotes.toList())
        }
    }

    private fun editNotes() {
        viewModelScope.launch {
            noteUseCases.archiveNotesUseCase(getSelectedList())
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
        val list = mutableListOf<Note>()
        selectedNotes.forEach { id ->
            if (id != null) {
                val note = noteUseCases.getNoteByIdUseCase(id)
                if (note != null) list.add(note)
            }
        }
        return list
    }

    private fun onNoteDeleted() {
        _state.value = state.value.copy(
            aNoteHasBeenDeleted = true
        )
    }
}