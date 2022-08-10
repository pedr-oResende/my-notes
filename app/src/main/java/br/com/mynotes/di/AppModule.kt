package br.com.mynotes.di

import android.app.Application
import androidx.room.Room
import br.com.mynotes.features.notes.data.data_source.NoteDatabase
import br.com.mynotes.features.notes.data.repository.NoteRepositoryImpl
import br.com.mynotes.features.notes.domain.repository.NoteRepository
import br.com.mynotes.features.notes.domain.use_case.DeleteNotesUseCase
import br.com.mynotes.features.notes.domain.use_case.GetNotesUseCase
import br.com.mynotes.features.notes.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
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
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNotesUseCase = DeleteNotesUseCase(repository)
        )
    }

}