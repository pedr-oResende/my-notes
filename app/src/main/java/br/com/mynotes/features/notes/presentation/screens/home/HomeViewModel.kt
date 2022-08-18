package br.com.mynotes.features.notes.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import br.com.mynotes.MyNotesApp
import br.com.mynotes.R
import br.com.mynotes.commom.InvalidNoteException
import br.com.mynotes.commom.util.PreferencesKey
import br.com.mynotes.commom.util.PreferencesWrapper
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.use_case.NoteUseCases
import br.com.mynotes.features.notes.presentation.compose.navigation.Screens
import br.com.mynotes.features.notes.presentation.util.HomeEvent
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

    private val recentlyDeletedNotes = mutableListOf<Note>()
    private var getNotesJob: Job? = null
    private val _notesUI = mutableStateOf(NotesUI())
    val notesUI: State<NotesUI> = _notesUI

    private val _eventFlow = MutableSharedFlow<NotesEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getNotes()
        _notesUI.value = notesUI.value.copy(
            isInGridMode = PreferencesWrapper.instance?.getBoolean(PreferencesKey.LAYOUT_STATE_KEY)
                ?: true
        )
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.DeleteNote -> {
                recentlyDeletedNotes.addAll(selectedNotes())
                deleteNotes()
                disableSelectedMode()
            }
            is HomeEvent.RestoreNotes -> {
                editNotes(recentlyDeletedNotes)
                recentlyDeletedNotes.removeAll { true }
                disableSelectedMode()
            }
            is HomeEvent.SelectNote -> {
                selectNote(event.note)
            }
            is HomeEvent.ToggleListView -> {
                toggleListView()
            }
            is HomeEvent.ToggleCloseSelection -> {
                disableSelectedMode()
            }
            is HomeEvent.ArchiveNote -> {
                editNotes(selectedNotes().map { note ->
                    note.copy(
                        isArchived = true
                    )
                })
                disableSelectedMode()
            }
            is HomeEvent.ToggleMarkPin -> {
                selectedNotes().let { notes ->
                    editNotes(notes.map { note ->
                        note.copy(
                            isFixed = !notes.all { it.isFixed }
                        )
                    })
                }
                disableSelectedMode()
            }
            is HomeEvent.OnNoteDeleted -> {
                onNoteDeleted()
            }
            is HomeEvent.ToggleMenuMore -> {
                _notesUI.value = notesUI.value.copy(
                    showMenuMore = !notesUI.value.showMenuMore
                )
            }
            is HomeEvent.SearchTextChanged -> {
                _notesUI.value = notesUI.value.copy(
                    searchNotesText = event.text
                )
            }
        }
    }

    private fun getNotes() {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesUseCase().onEach { notes ->
            _notesUI.value = notesUI.value.copy(
                notes = notes.filter { note -> !note.isArchived }
            )
        }.launchIn(viewModelScope)
    }

    private fun deleteNotes() {
        val context = MyNotesApp.getContext()!!
        viewModelScope.launch {
            noteUseCases.deleteNotesUseCase(selectedNotes().map { it.id })
            _eventFlow.emit(
                NotesEvents.ShowUndoSnackBar(
                    text = context.getString(R.string.notes_list_notes_removed_message),
                    label = context.getString(R.string.label_undo)
                )
            )
        }
    }

    private fun editNotes(notes: List<Note>) {
        notes.forEach { note ->
            viewModelScope.launch {
                try {
                    noteUseCases.updateNotesUseCase(note)
                } catch (e: InvalidNoteException) {
                    _eventFlow.emit(
                        NotesEvents.ShowSnackBar(
                            message = e.message ?: MyNotesApp.getContext()
                                ?.getString(R.string.save_note_error_message) ?: ""
                        )
                    )
                }
            }
        }
    }

    private fun selectNote(note: Note) {
        val selectedNotes: List<Note> = notesUI.value.notes.let { notes ->
            notes.map {
                it.copy(
                    isSelected = if (it.id == note.id) !it.isSelected else it.isSelected
                )
            }
        }
        _notesUI.value = notesUI.value.copy(
            notes = selectedNotes,
            isInSelectedMode = selectedNotes.any { it.isSelected },
            isPinFilled = selectedNotes.all { it.isFixed }
        )
    }

    fun onItemLongClick(note: Note) {
        onEvent(HomeEvent.SelectNote(note))
    }

    private fun toggleListView() {
        val value = !notesUI.value.isInGridMode
        _notesUI.value = notesUI.value.copy(
            isInGridMode = value
        )
        PreferencesWrapper.instance?.putBoolean(
            key = PreferencesKey.LAYOUT_STATE_KEY,
            value = value
        )
    }

    private fun disableSelectedMode() {
        _notesUI.value = notesUI.value.copy(
            isInSelectedMode = false,
            notes = notesUI.value.notes.map {
                it.copy(
                    isSelected = false
                )
            }
        )
    }

    fun goToDetail(navHostController: NavHostController, note: Note) {
        Screens.NoteDetail.navigateWithArgument(
            navHostController = navHostController,
            argumentValue = note
        )
    }

    fun getNotesListFiltered(): List<Note> {
        return notesUI.value.notes.filter { note ->
            note.title.contains(notesUI.value.searchNotesText, ignoreCase = true) ||
            note.content.contains(notesUI.value.searchNotesText, ignoreCase = true)
        }
    }

    private fun onNoteDeleted() {
        _notesUI.value = notesUI.value.copy(
            aNoteHasBeenDeleted = true
        )
    }

    private fun selectedNotes(): List<Note> = notesUI.value.notes.filter { it.isSelected }
}