package br.com.mynotes.di

import android.app.Application
import androidx.room.Room
import br.com.mynotes.features.notes.data.local.database.NoteDatabase
import br.com.mynotes.features.notes.data.repository.NoteRepositoryImpl
import br.com.mynotes.features.notes.domain.repository.NoteRepository
import br.com.mynotes.features.notes.domain.use_case.*
import br.com.mynotes.features.notes.domain.use_case.wrapper.ArchiveUseCases
import br.com.mynotes.features.notes.domain.use_case.wrapper.HomeUseCases
import br.com.mynotes.features.notes.domain.use_case.wrapper.TrashCanUseCases
import br.com.mynotes.features.notes.presentation.work_manager.DeleteNoteScheduler
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
    fun providesTrashCanUseCases(repository: NoteRepository): TrashCanUseCases {
        return TrashCanUseCases(
            getDeletedNotesUseCase = GetDeletedNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            restoreNoteUseCase = RestoreNoteUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun providesArchiveUseCases(repository: NoteRepository): ArchiveUseCases {
        return ArchiveUseCases(
            getArchivedNotesUseCase = GetArchivedNotesUseCase(repository),
            unarchiveNoteUseCase = UnarchiveNoteUseCase(repository),
            moveToTrashCanUseCase = MoveToTrashCanUseCase(repository, DeleteNoteScheduler()),
            restoreNoteUseCase = RestoreNoteUseCase(repository),
            markPinUseCase = MarkPinUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun providesHomeUseCases(repository: NoteRepository): HomeUseCases {
        return HomeUseCases(
            getMainNotesUseCase = GetMainNotesUseCase(repository),
            moveToTrashCanUseCase = MoveToTrashCanUseCase(repository, DeleteNoteScheduler()),
            archiveNoteUseCase = ArchiveNoteUseCase(repository),
            restoreNoteUseCase = RestoreNoteUseCase(repository),
            markPinUseCase = MarkPinUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideNoteDetailUseCases(repository: NoteRepository): NoteDetailUseCases {
        return NoteDetailUseCases(
            addNoteUseCase = AddNoteUseCase(repository),
            archiveNoteUseCase = ArchiveNoteUseCase(repository),
            unarchiveNoteUseCase = UnarchiveNoteUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            moveToTrashCanUseCase = MoveToTrashCanUseCase(repository, DeleteNoteScheduler()),
            restoreNoteUseCase = RestoreNoteUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideWorkManagerUseCases(repository: NoteRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(repository)
    }
}