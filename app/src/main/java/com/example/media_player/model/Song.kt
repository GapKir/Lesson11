package com.example.media_player.model

import java.time.Duration

data class Song(
    val id: Long,
    val albumPhoto: Int,
    val songName: String,
    val singer: Singer,
    val duration: Duration,
    val pathToSong: Int
) {
    var isPlaying = false

}
