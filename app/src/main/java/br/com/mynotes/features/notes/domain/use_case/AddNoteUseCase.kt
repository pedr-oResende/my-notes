package br.com.mynotes.features.notes.domain.use_case

import br.com.mynotes.MyNotesApp
import br.com.mynotes.R
import br.com.mynotes.commom.InvalidNoteException
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        val context = MyNotesApp.getContext()!!
        if (note.title.isBlank()) {
            throw InvalidNoteException(context.getString(R.string.title_empty_error_message))
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException(context.getString(R.string.content_empty_error_message))
        }
        repository.insertNote(note)
    }
}