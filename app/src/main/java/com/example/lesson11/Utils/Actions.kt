package com.example.lesson11.Utils;

import com.example.lesson11.model.Singer;
import com.example.lesson11.model.Song;

public interface Actions {
    void goToSettings();
    void goToHome();
    void goToSingers();
    void playSong(Song song);
    void showSingleSinger(Singer singer);
    void showFilteredSongsFromSinger(Singer singer);
}
