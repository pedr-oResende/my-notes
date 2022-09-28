package br.com.mynotes.features.notes.domain.use_case.wrapper

import br.com.mynotes.features.notes.domain.use_case.*

data class TrashCanUseCases(
    val getDeletedNotesUseCase: GetDeletedNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val restoreNoteUseCase: RestoreNoteUseCase
)
