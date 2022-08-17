package br.com.mynotes.features.notes.domain.use_case

import br.com.mynotes.commom.InvalidNoteException
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank() && note.content.isBlank()) throw InvalidNoteException()
        repository.insertNote(note)
    }
}