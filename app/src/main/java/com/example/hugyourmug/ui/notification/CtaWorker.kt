package com.example.hugyourmug.ui.notifications

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class CtaWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        NotificationHelper.showCallToAction(applicationContext)
        return Result.success()
    }
}
