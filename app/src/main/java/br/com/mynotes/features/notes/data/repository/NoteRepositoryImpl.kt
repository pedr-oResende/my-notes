package br.com.mynotes.features.notes.data.repository

import br.com.mynotes.features.notes.data.data_source.NoteDao
import br.com.mynotes.features.notes.domain.model.Note
import br.com.mynotes.features.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getMainNotes(): Flow<List<Note>> {
        return dao.getMainNotes()
    }

    override fun getArchivedNotes(): Flow<List<Note>> {
        return dao.getArchivedNotes()
    }

    override fun getDeletedNotes(): Flow<List<Note>> {
        return dao.getDeletedNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(id: Int?) {
        dao.deleteNote(id)
    }

}