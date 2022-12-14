package br.com.mynotes.features.notes.domain.repository

import br.com.mynotes.features.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getMainNotes(): Flow<List<Note>>

    fun getArchivedNotes(): Flow<List<Note>>

    fun getDeletedNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int?): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(id: Int?)

}