package com.example.lesson11;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import com.example.lesson11.Utils.Actions;
import com.example.lesson11.Utils.Constants;
import com.example.lesson11.databinding.ActivityMainBinding;
import com.example.lesson11.model.Singer;
import com.example.lesson11.model.Song;
import com.example.lesson11.ui.PlaylistFragment;
import com.example.lesson11.ui.SettingsFragment;
import com.example.lesson11.ui.SingerFragment;

public class MainActivity extends AppCompatActivity implements Actions {
    private ActivityMainBinding binding;
    private MediaPlayer player;
    private boolean isPlaying = false;
    private Song currentSongPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initListeners();

    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }

    @Override
    public void goToSettings() {
        openFragment(new SettingsFragment());
        binding.bottomNavigationView.getMenu().findItem(R.id.btn_settings).setChecked(true);
    }

    @Override
    public void goToHome() {
        openFragment(new PlaylistFragment());
        binding.bottomNavigationView.getMenu().findItem(R.id.btn_playlist).setChecked(true);
    }

    @Override
    public void goToSingers() {
        openFragment(new SingerFragment());
        binding.bottomNavigationView.getMenu().findItem(R.id.btn_singer).setChecked(true);
    }

    @Override
    public void playSong(Song song) {
        if (player == null) {
            startSong(song);
        } else {
            if (!isPlayingSongMatching(song)){
                startAnotherSong(song);
            } else if (isPlaying) {
                pauseSong();
            } else {
                continuePlayingSong();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void showSingleSinger(Singer singer) {
        SingerFragment singerFragment = new SingerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_FOR_SEND_SINGER, singer);
        singerFragment.setArguments(bundle);
        openFragment(singerFragment);
        binding.bottomNavigationView.getMenu().findItem(R.id.btn_singer).setChecked(true);
    }

    @Override
    public void showFilteredSongsFromSinger(Singer singer) {
        PlaylistFragment playlistFragment = new PlaylistFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_FOR_SEND_SINGER, singer);
        playlistFragment.setArguments(bundle);
        openFragment(playlistFragment);
        binding.bottomNavigationView.getMenu().findItem(R.id.btn_playlist).setChecked(true);
    }

    private void startAnotherSong(Song song) {
        player.stop();
        player.release();
        player = MediaPlayer.create(this, song.getPathToSong());
        player.start();
        isPlaying = true;
        currentSongPlaying = song;
    }

    private void continuePlayingSong() {
        player.start();
        isPlaying = true;
    }

    private void pauseSong() {
        player.pause();
        isPlaying = false;
    }

    private void startSong(Song song) {
        player = MediaPlayer.create(this, song.getPathToSong());
        player.start();
        isPlaying = true;
        currentSongPlaying = song;
    }
    private void stopPlayer(){
        if (player != null) {
            player.release();
            player = null;
            isPlaying = false;
            currentSongPlaying = null;
        }
    }

    private boolean isPlayingSongMatching(Song song) {
        return currentSongPlaying.getId() == song.getId();
    }

    private void initView() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragments_container, new PlaylistFragment())
                .commit();
    }

    private void initListeners() {
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.btn_settings) {
                goToSettings();
            } else if (item.getItemId() == R.id.btn_playlist) {
                goToHome();
            } else {
                goToSingers();
            }
            return true;
        });
    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragments_container, fragment)
                .commit();
    }
}