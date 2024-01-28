package com.example.lesson11.Utils

import com.example.lesson11.model.Singer
import com.example.lesson11.model.Song

interface Actions {
    fun goToSettings()
    fun goToHome()
    fun goToSingers()
    fun playSong(song: Song)
    fun showSingleSinger(singer: Singer)
    fun showFilteredSongsFromSinger(singer: Singer)
}
