package com.example.media_player

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.media_player.model.SingersRepository
import com.example.media_player.model.SongsRepository

@RequiresApi(api = Build.VERSION_CODES.O)
class App : Application() {
    val songsRepository = SongsRepository()
    val singersRepository = SingersRepository()
}
