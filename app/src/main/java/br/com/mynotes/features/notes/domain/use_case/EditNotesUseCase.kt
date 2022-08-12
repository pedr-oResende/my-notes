package br.com.mynotes.features.notes.domain.use_case

import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class EditNotesUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(notes: List<Note>):Flow<Long> {
        notes.forEachIndexed { index, note ->
            if (index == notes.size) return@forEachIndexed
            repository.insertNote(note)
        }
        return repository.insertNote(notes.last())
    }
}