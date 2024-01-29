package com.example.media_player.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.media_player.App
import com.example.media_player.Utils.Actions
import com.example.media_player.Utils.Constants
import com.example.media_player.adapters.PlayListAdapter
import com.example.media_player.databinding.FragmentPlaylistBinding
import com.example.media_player.model.Singer
import com.example.media_player.model.Song
import com.example.media_player.model.SongsRepository

typealias PlayingSongListener = (Song) -> Unit
class PlaylistFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistBinding
    private lateinit var adapter: PlayListAdapter
    private lateinit var songsRepository: SongsRepository
    private lateinit var actions: Actions

    val currentPlayingSongListener: PlayingSongListener = {song ->
        adapter.updateSongState(song)
    }
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroyView() {
        super.onDestroyView()
        adapter.setSongList(songsRepository.getSongList())
    }

    private fun initListeners() {
        binding.topAppBar.setOnMenuItemClickListener { _ ->
            actions.goToSettings()
            true
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private fun initView() {
        val app = requireActivity().applicationContext as App
        songsRepository = app.songsRepository
        actions = requireActivity() as Actions
        createAdapter()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun createAdapter() {
        adapter = PlayListAdapter(actions)
        if (arguments == null) {
            adapter.setSongList(songsRepository.getSongList())
        } else {
            filterSongs()
        }
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recycler.layoutManager = layoutManager
        binding.recycler.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun filterSongs() {
        val singer = requireArguments().getParcelable(Constants.KEY_FOR_SEND_SINGER, Singer::class.java)
        val basicList = songsRepository.getSongList()
        val filteredList: MutableList<Song> = ArrayList()
        for (song in basicList) {
            if (song.singer.name == singer!!.name) {
                filteredList.add(song)
            }
        }
        adapter.setSongList(filteredList)
    }
}
