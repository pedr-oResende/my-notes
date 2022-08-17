package br.com.mynotes.features.notes.domain.use_case

import br.com.mynotes.features.notes.domain.repository.NoteRepository

class DeleteNotesUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(ids: List<Int?>) {
        repository.deleteMultipleNotes(ids)
    }
}