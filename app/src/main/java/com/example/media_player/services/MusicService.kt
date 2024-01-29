package com.example.media_player.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.media_player.model.Song
import com.example.media_player.ui.PlayingSongListener

class MusicService: Service() {
    private var myBinder = MyBinder()
    private var player: MediaPlayer? = null
    private var isPlaying = false
    private var currentSongPlaying: Song? = null
    private lateinit var notificationHelper: NotificationHelper
    private val currentSongListeners = mutableSetOf<PlayingSongListener>()
    override fun onCreate() {
        super.onCreate()
        notificationHelper = NotificationHelper(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            PlayerActions.PLAY.name -> playSong(currentSongPlaying!!)
            PlayerActions.STOP.name -> stopPlayer()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder {
        return myBinder
    }

    fun playSong(song: Song) {
        if (player == null) {
            startSong(song)
        } else {
            if (!isPlayingSongMatching(song)) {
                startAnotherSong(song)
            } else if (isPlaying) {
                pauseSong()
            } else {
                continuePlayingSong()
            }
        }
    }

    fun setCallbacks(callback: PlayingSongListener){
        currentSongListeners.add(callback)
    }

    private fun startSong(song: Song) {
        player = MediaPlayer.create(this, song.pathToSong)
        player!!.start()
        isPlaying = true
        currentSongPlaying = song
        notifyListeners()
        showNotification()
    }

    private fun startAnotherSong(song: Song) {
        player!!.stop()
        player!!.release()
        player = MediaPlayer.create(this, song.pathToSong)
        player!!.start()
        isPlaying = true
        currentSongPlaying = song
        notifyListeners()
        showNotification()
    }

    private fun continuePlayingSong() {
        player!!.start()
        isPlaying = true
        notifyListeners()
        showNotification()
    }

    private fun pauseSong() {
        player!!.pause()
        isPlaying = false
        notifyListeners()
        showNotification(showPauseIcon = false)
    }

    private fun stopPlayer() {
        if (player != null) {
            player!!.release()
            player = null
            isPlaying = false
            notifyListeners()
            currentSongPlaying = null
        }
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun isPlayingSongMatching(song: Song): Boolean {
        return currentSongPlaying!!.id == song.id
    }

    private fun showNotification(showPauseIcon: Boolean = true){
        val notification = notificationHelper.createMusicNotification(currentSongPlaying!!, showPauseIcon)
        startForeground(NotificationHelper.NOTIFICATION_ID, notification)
    }

    private fun notifyListeners(){
        currentSongListeners.forEach { it.invoke(currentSongPlaying!!) }
    }

    inner class MyBinder: Binder(){
        fun getService(): MusicService{
            return this@MusicService
        }
    }

    companion object{
        enum class PlayerActions { PLAY, OPEN_PLAYER, STOP }
    }
}