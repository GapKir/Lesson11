package com.example.media_player.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.media_player.R
import com.example.media_player.Utils.Actions
import com.example.media_player.databinding.ItemSongBinding
import com.example.media_player.model.Singer
import com.example.media_player.model.Song

class PlayListAdapter(
    private val actions: Actions
) : RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder>(), View.OnClickListener {
    private var songList: List<Song>? = null
    private var currentlyPlayingSong: Song? = null
    fun setSongList(songList: List<Song>) {
        this.songList = songList
        notifyItemChanged(0, songList.size)
    }

    fun updateSongState(song: Song) {
        if (currentlyPlayingSong == null) {
            song.isPlaying = true
            notifyItemChanged(songList!!.indexOf(song))
        } else if (currentlyPlayingSong!!.id != song.id) {
            currentlyPlayingSong!!.isPlaying = false
            song.isPlaying = !song.isPlaying
            notifyItemChanged(songList!!.indexOf(currentlyPlayingSong!!))
            notifyItemChanged(songList!!.indexOf(song))
        } else {
            currentlyPlayingSong!!.isPlaying = !currentlyPlayingSong!!.isPlaying
            notifyItemChanged(songList!!.indexOf(currentlyPlayingSong!!))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSongBinding.inflate(inflater, parent, false)
        binding.btnPlaySong.setOnClickListener(this)
        binding.itemSong.setOnClickListener(this)
        return PlayListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        val song = songList!![position]
        holder.bind(song)
    }

    override fun getItemCount(): Int {
        return songList!!.size
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_play_song) {
            val song = view.tag as Song
            playSong(song)
        } else if (view.id == R.id.item_song) {
            val singer = view.tag as Singer
            actions.showSingleSinger(singer)
        }
    }

    private fun playSong(song: Song) {
        actions.playSong(song)
        currentlyPlayingSong = song
    }

    class PlayListViewHolder(private val binding: ItemSongBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(song: Song) {
            binding.itemSong.tag = song
            binding.btnPlaySong.tag = song
            binding.itemSong.tag = song.singer
            binding.ivAlbumPhoto.setImageResource(song.albumPhoto)
            binding.tvSongName.text = song.songName
            binding.tvSingerName.text = song.singer.name
            binding.btnPlaySong.setImageResource(if (song.isPlaying) R.drawable.btn_pause else R.drawable.play_list)
        }
    }
}
