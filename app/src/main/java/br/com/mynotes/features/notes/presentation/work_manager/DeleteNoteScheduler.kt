package br.com.mynotes.features.notes.presentation.work_manager

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit


class DeleteNoteScheduler {

    fun setupDeleteNoteWorker(
        context: Context,
        noteId: Int?
    )  {
        val request = OneTimeWorkRequestBuilder<DeleteNoteWorker>()
            .setInputData(workDataOf(WorkerKeys.NOTE_ID to noteId))
            .setInitialDelay(DAYS_FOR_DELETE_NOTE, TimeUnit.MILLISECONDS)
            .build()
        WorkManager.getInstance(context).enqueue(request)
    }

    companion object {
        const val DAYS_FOR_DELETE_NOTE = 7L * 24 * 60 * 60 * 1000L
    }

}