package com.example.lesson11.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson11.App
import com.example.lesson11.Utils.Actions
import com.example.lesson11.Utils.Constants
import com.example.lesson11.adapters.PlayListAdapter
import com.example.lesson11.databinding.FragmentPlaylistBinding
import com.example.lesson11.model.Singer
import com.example.lesson11.model.Song
import com.example.lesson11.model.SongService

class PlaylistFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistBinding
    private lateinit var adapter: PlayListAdapter
    private lateinit var songService: SongService
    private lateinit var actions: Actions
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
        adapter.setSongList(songService.getSongList())
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
        songService = app.songService
        actions = requireActivity() as Actions
        createAdapter()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun createAdapter() {
        adapter = PlayListAdapter(actions)
        if (arguments == null) {
            adapter.setSongList(songService.getSongList())
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
        val basicList = songService.getSongList()
        val filteredList: MutableList<Song> = ArrayList()
        for (song in basicList) {
            if (song.singer.name == singer!!.name) {
                filteredList.add(song)
            }
        }
        adapter.setSongList(filteredList)
    }
}
