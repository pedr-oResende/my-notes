package br.com.mynotes.features.notes.ui.screens.main

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import br.com.mynotes.R
import br.com.mynotes.commom.InvalidNoteException
import br.com.mynotes.commom.util.PreferencesKey
import br.com.mynotes.commom.util.PreferencesWrapper
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.use_case.NoteUseCases
import br.com.mynotes.features.notes.ui.compose.navigation.Screens
import br.com.mynotes.features.notes.ui.screens.main.state.MainUIEvents
import br.com.mynotes.features.notes.ui.screens.main.state.NotesActions
import br.com.mynotes.features.notes.ui.screens.main.state.NotesUI
import br.com.mynotes.features.notes.ui.screens.main.state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    application: Application
) : AndroidViewModel(application) {

    private val recentlyDeletedNotes = mutableListOf<Note>()
    private var getNotesJob: Job? = null
    private val _notesUI = mutableStateOf(NotesUI())
    val notesUI: State<NotesUI> = _notesUI

    private val _eventFlow = MutableSharedFlow<NotesActions>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        val screenState = ScreenState.getScreenStateEnum(
            value = PreferencesWrapper.instance?.getString(PreferencesKey.SCREEN_STATE_KEY)
        )
        _notesUI.value = notesUI.value.copy(
            isInGridMode = PreferencesWrapper.instance?.getBoolean(
                key = PreferencesKey.LAYOUT_STATE_KEY
            ) ?: true,
            showAutoDeleteMessage = PreferencesWrapper.instance?.getBoolean(
                key = PreferencesKey.SHOW_AUTO_DELETE_MESSAGE_KEY
            ) ?: true,
            screenState = screenState
        )
        getNotes(screenState)
    }

    fun onEvent(event: MainUIEvents) {
        when (event) {
            is MainUIEvents.MoveNoteToTrashCan -> {
                recentlyDeletedNotes.addAll(selectedNotes())
                moveToTrashCan(selectedNotes().map { note ->
                    note.copy(
                        isDeleted = true,
                        isArchived = false
                    )
                })
                disableSelectedMode()
            }
            is MainUIEvents.RestoreNotes -> {
                editNotes(recentlyDeletedNotes)
                recentlyDeletedNotes.removeAll { true }
                disableSelectedMode()
            }
            is MainUIEvents.SelectNote -> {
                selectNote(event.note)
            }
            is MainUIEvents.ToggleListView -> {
                toggleListView()
            }
            is MainUIEvents.ToggleCloseSelection -> {
                disableSelectedMode()
            }
            is MainUIEvents.ArchiveNote -> {
                editNotes(selectedNotes().map { note ->
                    note.copy(
                        isArchived = event.archive,
                        isDeleted = false
                    )
                })
                disableSelectedMode()
            }
            is MainUIEvents.ToggleMarkPin -> {
                selectedNotes().let { notes ->
                    editNotes(notes.map { note ->
                        note.copy(
                            isFixed = !notes.all { it.isFixed },
                            isArchived = if (note.isArchived) false else note.isArchived
                        )
                    })
                }
                disableSelectedMode()
            }
            is MainUIEvents.OnNoteDeleted -> {
                onNoteDeleted()
            }
            is MainUIEvents.ToggleMenuMore -> {
                _notesUI.value = notesUI.value.copy(
                    showMenuMore = !notesUI.value.showMenuMore
                )
            }
            is MainUIEvents.SearchTextChanged -> {
                _notesUI.value = notesUI.value.copy(
                    searchNotesText = event.text
                )
            }
            is MainUIEvents.ChangeScreen -> {
                PreferencesWrapper.instance?.putString(
                    key = PreferencesKey.SCREEN_STATE_KEY,
                    value = event.screen.value
                )
                _notesUI.value = notesUI.value.copy(
                    screenState = event.screen,
                    showMenuMore = false
                )
                getNotes(event.screen)
            }
            is MainUIEvents.DeleteNotes -> {
                deleteNotes(selectedNotes())
                onEvent(MainUIEvents.ToggleMenuMore)
                disableSelectedMode()
            }
            is MainUIEvents.ClearTrashCan -> {
                deleteNotes(notesUI.value.notes)
                onEvent(MainUIEvents.ToggleMenuMore)
            }
            is MainUIEvents.RestoreFromTrashCan -> {
                editNotes(selectedNotes().map { note ->
                    note.copy(
                        isDeleted = false,
                        isArchived = false
                    )
                })
                disableSelectedMode()
            }
            is MainUIEvents.CloseAutoDeleteMessage -> {
                _notesUI.value = notesUI.value.copy(
                    showAutoDeleteMessage = false
                )
                PreferencesWrapper.instance?.putBoolean(
                    key = PreferencesKey.SHOW_AUTO_DELETE_MESSAGE_KEY,
                    value = false
                )
            }
        }
    }

    private fun getNotes(screenState: ScreenState) {
        when (screenState) {
            ScreenState.HomeScreen -> {
                getMainNotes()
            }
            ScreenState.ArchiveScreen -> {
                getArchivedNotes()
            }
            ScreenState.TrashCanScreen -> {
                getDeletedNotes()
            }
        }
    }

    private fun getMainNotes() {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesUseCase().onEach { notes ->
            _notesUI.value = notesUI.value.copy(notes = notes)
        }.launchIn(viewModelScope)
    }

    private fun getArchivedNotes() {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getArchivedNotesUseCase().onEach { notes ->
            _notesUI.value = notesUI.value.copy(notes = notes)
        }.launchIn(viewModelScope)
    }

    private fun getDeletedNotes() {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getDeletedNotesUseCase().onEach { notes ->
            _notesUI.value = notesUI.value.copy(notes = notes)
        }.launchIn(viewModelScope)
    }

    private fun moveToTrashCan(notes: List<Note>) {
        notes.forEach { note ->
            viewModelScope.launch {
                noteUseCases.moveToTrashCanUseCase(
                    note = note,
                    context = getApplication<Application>().applicationContext
                )
                _eventFlow.emit(
                    NotesActions.ShowUndoSnackBar(
                        text = getApplication<Application>().applicationContext.getString(R.string.notes_list_notes_removed_message),
                        label = getApplication<Application>().applicationContext.getString(R.string.label_undo)
                    )
                )
            }
        }
    }

    private fun deleteNotes(notes: List<Note>) {
        notes.forEach { note ->
            viewModelScope.launch {
                noteUseCases.deleteNoteUseCase(note.id)
            }
        }
    }

    private fun editNotes(notes: List<Note>) {
        notes.forEach { note ->
            viewModelScope.launch {
                try {
                    noteUseCases.updateNotesUseCase(note)
                } catch (e: InvalidNoteException) {
                    _eventFlow.emit(
                        NotesActions.ShowSnackBar(
                            message = e.message
                                ?: getApplication<Application>().applicationContext.getString(R.string.save_note_error_message)
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

    fun selectedNotesSize(): Int = selectedNotes().size
}