package br.com.mynotes.features.notes.domain.use_case

import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.repository.NoteRepository

class EditNotesUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(notes: List<Note>) {
        notes.forEach { note ->
            repository.insertNote(note)
        }
    }
}