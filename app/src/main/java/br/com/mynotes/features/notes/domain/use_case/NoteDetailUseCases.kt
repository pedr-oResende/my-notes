package br.com.mynotes.features.notes.domain.use_case

data class NoteDetailUseCases(
    val addNoteUseCase: AddNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val moveToTrashCanUseCase: MoveToTrashCanUseCase,
    val archiveNoteUseCase: ArchiveNoteUseCase
)
