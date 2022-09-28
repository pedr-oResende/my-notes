package br.com.mynotes.features.notes.domain.use_case.wrapper

import br.com.mynotes.features.notes.domain.use_case.*

data class HomeUseCases(
    val getMainNotesUseCase: GetMainNotesUseCase,
    val moveToTrashCanUseCase: MoveToTrashCanUseCase,
    val archiveNoteUseCase: ArchiveNoteUseCase,
    val restoreNoteUseCase: RestoreNoteUseCase,
    val markPinUseCase: MarkPinUseCase
)