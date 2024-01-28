package com.example.lesson11.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson11.R;
import com.example.lesson11.Utils.Actions;
import com.example.lesson11.databinding.ItemSongBinding;
import com.example.lesson11.model.Singer;
import com.example.lesson11.model.Song;

import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> implements View.OnClickListener {
    private List<Song> songList;
    private Actions actions;
    private Song currentlyPlayingSong;


    public void setSongList(List<Song> songList) {
        this.songList = songList;
        notifyItemChanged(0, songList.size());
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }


    @NonNull
    @Override
    public PlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSongBinding binding = ItemSongBinding.inflate(inflater, parent, false);

        binding.btnPlaySong.setOnClickListener(this);
        binding.itemSong.setOnClickListener(this);

        return new PlayListViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull PlayListViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_play_song) {
            Song song = (Song) view.getTag();
            playSong(song);
        } else if (view.getId() == R.id.item_song){
            Singer singer = (Singer) view.getTag();
            actions.showSingleSinger(singer);
        }
    }

    private void playSong(Song song) {
        updateSongState(song);
        actions.playSong(song);
        currentlyPlayingSong = song;
    }


    private void updateSongState(Song song) {
        if (currentlyPlayingSong == null) {
            song.setPlaying(true);
            notifyItemChanged(songList.indexOf(song));
        } else if (currentlyPlayingSong.getId() != song.getId()){
            currentlyPlayingSong.setPlaying(false);
            song.setPlaying(!song.isPlaying());
            notifyItemChanged(songList.indexOf(currentlyPlayingSong));
            notifyItemChanged(songList.indexOf(song));
        } else {
            currentlyPlayingSong.setPlaying(!currentlyPlayingSong.isPlaying());
            notifyItemChanged(songList.indexOf(currentlyPlayingSong));
        }
    }

    static class PlayListViewHolder extends RecyclerView.ViewHolder {
        private final ItemSongBinding binding;

        public PlayListViewHolder(ItemSongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Song song) {

            binding.itemSong.setTag(song);
            binding.btnPlaySong.setTag(song);
            binding.itemSong.setTag(song.getSinger());

            binding.ivAlbumPhoto.setImageResource(song.getAlbumPhoto());
            binding.tvSongName.setText(song.getSongName());
            binding.tvSingerName.setText(song.getSinger().getName());
            binding.btnPlaySong.setImageResource(song.isPlaying() ? R.drawable.btn_pause : R.drawable.play_list);
        }
    }
}
