package com.example.lesson11.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.example.lesson11.MainActivity
import com.example.lesson11.R
import com.example.lesson11.model.Song

class NotificationHelper(
    private val context: Context
) {
    private val mediaSession: MediaSessionCompat = MediaSessionCompat(context, MEDIA_SESSION)
    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private var hasAlreadyNotification = false

    init {
        createNotificationChannel()
    }

    fun createMusicNotification(song: Song, showPauseIcon: Boolean): Notification {
        val icon = if (showPauseIcon) R.drawable.btn_pause else R.drawable.play_list

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSound(null)
            .setContentTitle(song.songName)
            .setContentText(song.singer.name)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, song.albumPhoto))
            .setSmallIcon(android.R.color.transparent)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSession.sessionToken))
            .setPriority(NotificationManager.IMPORTANCE_LOW)
            .addAction(R.drawable.btn_home, "Home", createIntent(MusicService.Companion.PlayerActions.OPEN_PLAYER))
            .addAction(icon, "Pause", createIntent(MusicService.Companion.PlayerActions.PLAY))
            .addAction(R.drawable.btn_exit, "Exit", createIntent(MusicService.Companion.PlayerActions.STOP))

        hasAlreadyNotification = true
        return notificationBuilder.build()
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createIntent(action: MusicService.Companion.PlayerActions): PendingIntent {
        return when (action) {
            MusicService.Companion.PlayerActions.PLAY -> {
                val intent = Intent(context, MusicService::class.java).apply {
                    this.action = MusicService.Companion.PlayerActions.PLAY.name
                }
                PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_MUTABLE)
            }
            MusicService.Companion.PlayerActions.OPEN_PLAYER -> {
                val intent = Intent(context, MainActivity::class.java).apply {
                    this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            }
            MusicService.Companion.PlayerActions.STOP -> {
                val intent = Intent(context, MusicService::class.java).apply {
                    this.action = MusicService.Companion.PlayerActions.STOP.name
                }
                PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_MUTABLE)
            }

        }
    }

    companion object {
        private const val CHANNEL_ID = "CHANNEL_ID"
        private const val CHANNEL_NAME = "CHANNEL_NAME"
        const val NOTIFICATION_ID = 1
        private const val MEDIA_SESSION = "MEDIA_SESSION"
    }
}