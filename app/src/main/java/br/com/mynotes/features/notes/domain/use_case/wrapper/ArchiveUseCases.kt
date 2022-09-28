package br.com.mynotes.features.notes.domain.use_case.wrapper

import br.com.mynotes.features.notes.domain.use_case.*

data class ArchiveUseCases(
    val getArchivedNotesUseCase: GetArchivedNotesUseCase,
    val moveToTrashCanUseCase: MoveToTrashCanUseCase,
    val unarchiveNoteUseCase: UnarchiveNoteUseCase,
    val restoreNoteUseCase: RestoreNoteUseCase,
    val markPinUseCase: MarkPinUseCase
)