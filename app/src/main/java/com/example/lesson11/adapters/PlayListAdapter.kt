package com.example.lesson11.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson11.R
import com.example.lesson11.Utils.Actions
import com.example.lesson11.databinding.ItemSongBinding
import com.example.lesson11.model.Singer
import com.example.lesson11.model.Song

class PlayListAdapter : RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder>(), View.OnClickListener {
    private var songList: List<Song>? = null
    private var actions: Actions? = null
    private var currentlyPlayingSong: Song? = null
    fun setSongList(songList: List<Song>) {
        this.songList = songList
        notifyItemChanged(0, songList.size)
    }

    fun setActions(actions: Actions?) {
        this.actions = actions
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
            actions!!.showSingleSinger(singer)
        }
    }

    private fun playSong(song: Song) {
        updateSongState(song)
        actions!!.playSong(song)
        currentlyPlayingSong = song
    }

    private fun updateSongState(song: Song) {
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
