package br.com.mynotes.features.notes.presentation.screens.note_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.use_case.NoteDetailUseCases
import br.com.mynotes.features.notes.presentation.util.NoteDetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel@Inject constructor(
    private val noteDetailUseCases: NoteDetailUseCases
): ViewModel() {
    private val _state = mutableStateOf(NoteDetailState())
    val state: State<NoteDetailState> = _state

    fun onEvent(event: NoteDetailEvent) {
        when (event) {
            is NoteDetailEvent.ArchiveNote -> {

            }
            is NoteDetailEvent.DeleteNote -> {

            }
            is NoteDetailEvent.SaveNote -> {

            }
            is NoteDetailEvent.ToggleMarkPin -> {
                _state.value = state.value.copy(
                    isPinMarked = !state.value.isPinMarked
                )
            }
            is NoteDetailEvent.TitleChanged -> {
                _state.value = state.value.copy(
                    title = event.title
                )
            }
            is NoteDetailEvent.ContentChanged -> {
                _state.value = state.value.copy(
                    content = event.content
                )
            }
        }
    }

    fun loadNote(note: Note?) {
        onEvent(NoteDetailEvent.TitleChanged(note?.title ?: ""))
        onEvent(NoteDetailEvent.ContentChanged(note?.content ?: ""))
    }

}