package br.com.mynotes.features.notes.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.mynotes.features.notes.domain.model.Note

@Database(
    entities = [Note::class],
    version = 3,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_database"
    }
}