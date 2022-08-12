package br.com.mynotes.features.notes.data.repository

import br.com.mynotes.features.notes.data.data_source.NoteDao
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override suspend fun getNotes(): Flow<List<Note>> {
        return flow {
            dao.getNotes()
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note): Flow<Long> {
        return flow {
            dao.insertNote(note)
        }
    }

    override suspend fun deleteNote(note: Note): Flow<Long> {
        return flow {
            dao.deleteNote(note)
        }
    }
}