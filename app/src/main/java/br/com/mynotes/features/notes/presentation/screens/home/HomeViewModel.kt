package br.com.mynotes.features.notes.presentation.screens.home

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
import br.com.mynotes.commom.compose.navigation.Screens
import br.com.mynotes.features.notes.presentation.util.HomeUIEvents
import br.com.mynotes.features.notes.work_manager.DeleteNoteScheduler
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
    private val noteUseCases: NoteUseCases,
    private val deleteNoteScheduler: DeleteNoteScheduler,
    application: Application
) : AndroidViewModel(application) {

    private val recentlyDeletedNotes = mutableListOf<Note>()
    private var getNotesJob: Job? = null
    private val _notesUI = mutableStateOf(NotesUI())
    val notesUI: State<NotesUI> = _notesUI

    private val _eventFlow = MutableSharedFlow<NotesEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getNotes(ScreenState.HomeScreen)
        _notesUI.value = notesUI.value.copy(
            isInGridMode = PreferencesWrapper.instance?.getBoolean(PreferencesKey.LAYOUT_STATE_KEY)
                ?: true
        )
    }

    fun onEvent(event: HomeUIEvents) {
        when (event) {
            is HomeUIEvents.MoveNoteToTrashCan -> {
                recentlyDeletedNotes.addAll(selectedNotes())
                moveToTrashCan(selectedNotes().map { note ->
                    note.copy(
                        isDeleted = true,
                        isArchived = false
                    )
                })
                disableSelectedMode()
            }
            is HomeUIEvents.RestoreNotes -> {
                editNotes(recentlyDeletedNotes)
                recentlyDeletedNotes.removeAll { true }
                disableSelectedMode()
            }
            is HomeUIEvents.SelectNote -> {
                selectNote(event.note)
            }
            is HomeUIEvents.ToggleListView -> {
                toggleListView()
            }
            is HomeUIEvents.ToggleCloseSelection -> {
                disableSelectedMode()
            }
            is HomeUIEvents.ArchiveNote -> {
                editNotes(selectedNotes().map { note ->
                    note.copy(
                        isArchived = event.archive,
                        isDeleted = false
                    )
                })
                disableSelectedMode()
            }
            is HomeUIEvents.ToggleMarkPin -> {
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
            is HomeUIEvents.OnNoteDeleted -> {
                onNoteDeleted()
            }
            is HomeUIEvents.ToggleMenuMore -> {
                _notesUI.value = notesUI.value.copy(
                    showMenuMore = !notesUI.value.showMenuMore
                )
            }
            is HomeUIEvents.SearchTextChanged -> {
                _notesUI.value = notesUI.value.copy(
                    searchNotesText = event.text
                )
            }
            is HomeUIEvents.ChangeScreen -> {
                _notesUI.value = notesUI.value.copy(
                    screenState = event.screen,
                    showMenuMore = false
                )
                getNotes(event.screen)
            }
            is HomeUIEvents.DeleteNotes -> {
                deleteNotes(selectedNotes())
                onEvent(HomeUIEvents.ToggleMenuMore)
                disableSelectedMode()
            }
            is HomeUIEvents.ClearTrashCan -> {
                deleteNotes(notesUI.value.notes)
                onEvent(HomeUIEvents.ToggleMenuMore)
            }
            is HomeUIEvents.RestoreFromTrashCan -> {
                editNotes(selectedNotes().map { note ->
                    note.copy(
                        isDeleted = false,
                        isArchived = false
                    )
                })
                disableSelectedMode()
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
                noteUseCases.updateNotesUseCase(note)
                deleteNoteScheduler.setupDeleteNoteWorker(
                    context = getApplication<Application>().applicationContext,
                    noteId = note.id
                )
                _eventFlow.emit(
                    NotesEvents.ShowUndoSnackBar(
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
                        NotesEvents.ShowSnackBar(
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