package com.example.media_player.Utils

import com.example.media_player.model.Singer
import com.example.media_player.model.Song

interface Actions {
    fun goToSettings()
    fun goToHome()
    fun goToSingers()
    fun playSong(song: Song)
    fun showSingleSinger(singer: Singer)
    fun showFilteredSongsFromSinger(singer: Singer)
}
