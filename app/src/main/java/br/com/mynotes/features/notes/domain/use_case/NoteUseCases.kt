package br.com.mynotes.features.notes.domain.use_case

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNotesUseCase: DeleteNotesUseCase,
    val archiveNotesUseCase: EditNotesUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase
)
