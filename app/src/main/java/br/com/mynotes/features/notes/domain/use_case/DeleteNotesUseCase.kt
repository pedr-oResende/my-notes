package br.com.mynotes.features.notes.domain.use_case

import br.com.mynotes.features.notes.domain.repository.NoteRepository

class DeleteNotesUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(notes: List<Int?>) {
        notes.forEach{ id ->
            repository.deleteNote(id)
        }
    }
}