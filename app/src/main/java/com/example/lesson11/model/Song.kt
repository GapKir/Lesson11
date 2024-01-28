package com.example.lesson11.model;

import java.time.Duration;

public class Song {
    private long id;
    private int albumPhoto;
    private String songName;
    private Singer singer;
    private Duration duration;
    private int pathToSong;
    private boolean isPlaying = false;

    public Song(long id, int albumPhoto, String songName, Singer singer, Duration duration, int pathToSong) {
        this.id = id;
        this.albumPhoto = albumPhoto;
        this.songName = songName;
        this.singer = singer;
        this.duration = duration;
        this.pathToSong = pathToSong;
    }

    public long getId() {
        return id;
    }

    public int getAlbumPhoto() {
        return albumPhoto;
    }

    public String getSongName() {
        return songName;
    }

    public Singer getSinger() {
        return singer;
    }

    public Duration getDuration() {
        return duration;
    }


    public int getPathToSong() {
        return pathToSong;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
