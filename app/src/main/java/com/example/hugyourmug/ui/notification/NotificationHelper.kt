package com.example.hugyourmug.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hugyourmug.R
import kotlin.random.Random

object NotificationHelper {

    private const val CHANNEL_ID = "hugyourmug_notifications"
    private const val CHANNEL_NAME = "Hug Your Mug Notifications"

    private val callToActionMessages = listOf(
        "☕ Feeling tired? An Espresso might help!",
        "🔥 Late night? Stay focused with a Cappuccino",
        "❤️ Your favorite Latte is waiting for you",
        "😌 Take a break with a warm coffee",
        "💪 Stay motivated — grab your coffee now"
    )

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun showNotification(
        context: Context,
        title: String,
        message: String
    ) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_coffee)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(System.currentTimeMillis().toInt(), notification)
    }

    fun showCallToAction(context: Context) {
        val message = callToActionMessages[Random.nextInt(callToActionMessages.size)]
        showNotification(
            context = context,
            title = "Hug Your Mug ☕",
            message = message
        )
    }
}
