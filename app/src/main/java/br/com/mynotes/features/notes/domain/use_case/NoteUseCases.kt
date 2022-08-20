package br.com.mynotes.features.notes.domain.use_case

data class NoteUseCases(
    val getNotesUseCase: GetMainNotesUseCase,
    val getArchivedNotesUseCase: GetArchivedNotesUseCase,
    val getDeletedNotesUseCase: GetDeletedNotesUseCase,
    val updateNotesUseCase: AddNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
    val unarchiveNoteUseCase: UnarchiveNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase
)
