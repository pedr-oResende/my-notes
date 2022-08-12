package br.com.mynotes.features.notes.domain.use_case

data class NoteDetailUseCases(
    val addNotesUseCase: AddNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val archiveNoteUseCase: ArchiveNoteUseCase
)
