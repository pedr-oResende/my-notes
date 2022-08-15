package br.com.mynotes.features.notes.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import br.com.mynotes.MyNotesApp
import br.com.mynotes.R
import br.com.mynotes.commom.InvalidNoteException
import br.com.mynotes.commom.compose.navigation.Screens
import br.com.mynotes.commom.util.PreferencesKey
import br.com.mynotes.commom.util.PreferencesWrapper
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.use_case.NoteUseCases
import br.com.mynotes.features.notes.presentation.util.NotesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val selectedNotes = mutableStateListOf<Note>()
    private var getNotesJob: Job? = null
    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                disableSelectedMode(cleanSelectedList = false)
            }
            is NotesEvent.SelectNote -> {
                selectNote(event.note)
            }
            is NotesEvent.ToggleListView -> {
                toggleListView()
            }
            is NotesEvent.ToggleCloseSelection -> {
                disableSelectedMode()
            }
            is NotesEvent.ArchiveNote -> {
                editNotes(selectedNotes.map { note ->
                    note.copy(
                        isArchived = true
                    )
                })
                disableSelectedMode()
            }
            is NotesEvent.RestoreNotes -> {
                editNotes(selectedNotes)
                disableSelectedMode()
            }
            is NotesEvent.ToggleMarkPin -> {
                editNotes(selectedNotes.map { note ->
                    note.copy(
                        isFixed = !note.isFixed
                    )
                })
                disableSelectedMode()
            }
            is NotesEvent.OnNoteDeleted -> {
                onNoteDeleted()
            }
            is NotesEvent.ToggleMenuMore -> {
                _state.value = state.value.copy(
                    showMenuMore = !state.value.showMenuMore
                )
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
            noteUseCases.deleteNotesUseCase(selectedNotes.map { it.id })
            _eventFlow.emit(
                UIEvents.ShowUndoSnackBar(
                    text = "Algumas notas foram removidas",
                    label = "Desfazer"
                )
            )
        }
    }

    private fun editNotes(notes: List<Note>) {
        viewModelScope.launch {
            try {
                noteUseCases.editNotesUseCase(notes)
            } catch (e: InvalidNoteException) {
                _eventFlow.emit(
                    UIEvents.ShowSnackBar(
                        message = e.message ?: MyNotesApp.getContext()
                            ?.getString(R.string.save_note_error_message) ?: ""
                    )
                )
            }
        }
    }

    private fun selectNote(note: Note) {
        if (selectedNotes.contains(note)) {
            selectedNotes.removeAll { it.id == note.id }
        } else {
            selectedNotes.add(note)
        }
        _state.value = state.value.copy(
            isInSelectedMode = selectedNotes.isNotEmpty()
        )
    }

    fun isNoteSelected(note: Note): Boolean = selectedNotes.contains(note)

    fun onItemLongClick(note: Note) {
        onEvent(NotesEvent.SelectNote(note))
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

    private fun disableSelectedMode(cleanSelectedList: Boolean = true) {
        _state.value = state.value.copy(
            isInSelectedMode = false
        )
        selectedNotes.removeAll { cleanSelectedList }
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

    private fun onNoteDeleted() {
        _state.value = state.value.copy(
            aNoteHasBeenDeleted = true
        )
    }
}