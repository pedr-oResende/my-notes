package br.com.mynotes.features.notes.domain.use_case

import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.repository.NoteRepository

class DeleteNotesUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}