package com.example.lesson11;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.lesson11.model.SingersService;
import com.example.lesson11.model.SongService;

@RequiresApi(api = Build.VERSION_CODES.O)
public class App extends Application {
    public final SongService songService = new SongService();
    public final SingersService singersService = new SingersService();
}
