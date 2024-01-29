package com.example.media_player.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.media_player.Utils.Actions
import com.example.media_player.databinding.ItemSingerBinding
import com.example.media_player.model.Singer

class SingerListAdapter : RecyclerView.Adapter<SingerListAdapter.PlayListViewHolder>(),
    View.OnClickListener {
    private var singerList: List<Singer>? = null
    private var actions: Actions? = null
    fun setSingerList(singerList: List<Singer>?) {
        this.singerList = singerList
        notifyDataSetChanged()
    }

    fun setActions(actions: Actions?) {
        this.actions = actions
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSingerBinding.inflate(inflater, parent, false)
        binding.itemSinger.setOnClickListener(this)
        return PlayListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        val singer = singerList!![position]
        holder.bind(singer)
    }

    override fun getItemCount(): Int {
        return singerList!!.size
    }

    override fun onClick(view: View) {
        val singer = view.tag as Singer
        actions!!.showFilteredSongsFromSinger(singer)
    }

    inner class PlayListViewHolder(private val binding: ItemSingerBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun bind(singer: Singer) {
            binding.itemSinger.tag = singer
            binding.ivSingerPhoto.setImageResource(singer.singerPhoto)
            binding.tvSingerName.text = singer.name
            binding.tvSingerBirth.text = singer.birthCountry
        }
    }
}
