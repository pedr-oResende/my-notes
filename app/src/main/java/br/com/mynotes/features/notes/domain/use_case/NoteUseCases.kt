package br.com.mynotes.features.notes.domain.use_case

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNotesUseCase: DeleteNotesUseCase,
    val editNotesUseCase: EditNotesUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase
)
