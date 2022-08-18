package br.com.mynotes.features.notes.data.data_source

import androidx.room.*
import br.com.mynotes.features.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Query("SELECT * FROM note WHERE isArchived = :isArchived AND isDeleted = :isDeleted")
    fun getMainNotes(isArchived: Boolean = false, isDeleted: Boolean = false): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE isArchived = :archived")
    fun getArchivedNotes(archived: Boolean = true): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE isDeleted = :deleted")
    fun getDeletedNotes(deleted: Boolean = true): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun deleteNote(id: Int?)

}