package br.com.mynotes.di

import android.app.Application
import androidx.room.Room
import br.com.mynotes.features.notes.data.data_source.NoteDatabase
import br.com.mynotes.features.notes.data.repository.NoteRepositoryImpl
import br.com.mynotes.features.notes.domain.repository.NoteRepository
import br.com.mynotes.features.notes.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(database: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(database.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetMainNotesUseCase(repository),
            updateNotesUseCase = AddNoteUseCase(repository),
            getNoteByIdUseCase = GetNoteByIdUseCase(repository),
            unarchiveNoteUseCase = UnarchiveNoteUseCase(repository),
            getArchivedNotesUseCase = GetArchivedNotesUseCase(repository),
            getDeletedNotesUseCase = GetDeletedNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideNoteDetailUseCases(repository: NoteRepository): NoteDetailUseCases {
        return NoteDetailUseCases(
            addNoteUseCase = AddNoteUseCase(repository),
            archiveNoteUseCase = ArchiveNoteUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository)
        )
    }
}