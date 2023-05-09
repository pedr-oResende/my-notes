package br.com.mynotes.features.notes.domain.use_case

import android.content.Context
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.repository.NoteRepository
import br.com.mynotes.features.notes.presentation.work_manager.DeleteNoteScheduler

class MoveToTrashCanUseCase(
    private val repository: NoteRepository,
    private val deleteNoteScheduler: DeleteNoteScheduler
) {
    suspend operator fun invoke(note: Note, context: Context) {
        deleteNoteScheduler.setupDeleteNoteWorker(
            context = context,
            noteId = note.id
        )
        repository.insertNote(
            note.copy(
                isInTrashCan = true,
                isArchived = false
            )
        )
    }
}