package br.com.mynotes.features.notes.presentation.work_manager

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import br.com.mynotes.R
import br.com.mynotes.features.notes.domain.use_case.DeleteNoteUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

@HiltWorker
class DeleteNoteWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        startForegroundService()
        return withContext(Dispatchers.IO) {
            val noteId = workerParams.inputData.getInt(WorkerKeys.NOTE_ID, 0)
            if (noteId == 0) Result.failure()
            deleteNoteUseCase(noteId)
            Result.success()
        }
    }

    private suspend fun startForegroundService() {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, "delete_note_channel")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Deleting note...")
                    .build()
            )
        )
    }
}