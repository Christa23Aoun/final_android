package com.example.hugyourmug.ui.notifications

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class OrderConfirmationWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        NotificationHelper.showNotification(
            context = applicationContext,
            title = "☕ Order Confirmed",
            message = "We’ve started preparing your Hug Your Mug order."
        )
        return Result.success()
    }
}
