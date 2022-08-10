package br.com.mynotes.features.notes.domain.use_case

import br.com.mynotes.commom.InvalidNoteException
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.repository.NoteRepository
import br.com.mynotes.features.notes.presentation.util.NoteOrder
import br.com.mynotes.features.notes.presentation.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AddNoteUseCase(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Title is empty")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Content is empty")
        }
        repository.insertNote(note)
    }
}