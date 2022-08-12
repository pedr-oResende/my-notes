package br.com.mynotes.features.notes.domain.use_case

import br.com.mynotes.features.notes.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteId: Int?) {
        repository.deleteNote(noteId)
    }
}