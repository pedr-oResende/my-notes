package br.com.mynotes.features.notes.ui.screens.main

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import br.com.mynotes.commom.extensions.getArgument
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.ui.compose.navigation.Screens
import br.com.mynotes.features.notes.ui.screens.main.ui.MainUIEvents
import br.com.mynotes.features.notes.ui.screens.main.ui.NotesUI
import br.com.mynotes.features.notes.ui.screens.main.ui.SnackBarEvents
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

open class BaseViewModel (
    private val savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {

    protected val recentlyDeletedNotes = mutableListOf<Note>()
    protected var getNotesJob: Job? = null
    private val _notesUI = mutableStateOf(NotesUI())
    val notesUI: State<NotesUI> = _notesUI

    private val _eventFlow = MutableSharedFlow<SnackBarEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: MainUIEvents) {
        when (event) {
            is MainUIEvents.SelectNote -> {
                selectNote(event.note)
            }
            is MainUIEvents.ToggleCloseSelection -> {
                disableSelectedMode()
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
        }
    }

    fun getSnackBarMessage() = savedStateHandle.getArgument<String>(Screens.Home.argumentKey) ?: ""

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

    protected fun disableSelectedMode() {
        _notesUI.value = notesUI.value.copy(
            isInSelectedMode = false,
            notes = notesUI.value.notes.map {
                it.copy(
                    isSelected = false
                )
            }
        )
    }

    fun getNotesListFilteredByText(): List<Note> {
        return notesUI.value.notes.filter { note ->
            note.title.contains(notesUI.value.searchNotesText, ignoreCase = true) ||
                    note.content.contains(notesUI.value.searchNotesText, ignoreCase = true)
        }
    }

    protected fun selectedNotes(): List<Note> = notesUI.value.notes.filter { it.isSelected }

    fun selectedNotesSize(): Int = selectedNotes().size

    fun onItemClick(note: Note, navHostController: NavHostController) {
        if (notesUI.value.isInSelectedMode)
            onEvent(MainUIEvents.SelectNote(note))
        else
            Screens.NoteDetail.navigateWithArgument(
                navHostController = navHostController,
                argumentValue = note
            )
    }

    fun onItemLongClick(note: Note) {
        onEvent(MainUIEvents.SelectNote(note))
    }

    protected fun getNotes() = notesUI.value.notes

    protected fun setNotes(notes: List<Note>) {
        _notesUI.value = notesUI.value.copy(notes = notes)
    }

    protected suspend fun emitSnackBarEvent(event: SnackBarEvents) {
        _eventFlow.emit(event)
    }
}