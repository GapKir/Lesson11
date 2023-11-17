package com.example.lesson11.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson11.App;
import com.example.lesson11.Utils.Actions;
import com.example.lesson11.Utils.Constants;
import com.example.lesson11.adapters.PlayListAdapter;
import com.example.lesson11.databinding.FragmentPlaylistBinding;
import com.example.lesson11.model.Singer;
import com.example.lesson11.model.Song;
import com.example.lesson11.model.SongService;

import java.util.ArrayList;
import java.util.List;

public class PlaylistFragment extends Fragment {
    private FragmentPlaylistBinding binding;
    private PlayListAdapter adapter;
    private SongService songService;
    private Actions actions;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false);
        initView();

        return binding.getRoot();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.setSongList(songService.getSongList());
    }

    private void initListeners() {
        binding.topAppBar.setOnMenuItemClickListener(item -> {
            actions.goToSettings();
            return true;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView() {
        App app = (App)requireActivity().getApplicationContext();
        songService = app.songService;
        actions = (Actions) requireActivity();

        createAdapter();


    }

    private void createAdapter() {
        adapter = new PlayListAdapter();
        if (getArguments() == null) {
            adapter.setSongList(songService.getSongList());
        } else {
            filterSongs();
        }
        adapter.setActions(actions);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setAdapter(adapter);
    }

    private void filterSongs() {
        Singer singer = getArguments().getParcelable(Constants.KEY_FOR_SEND_SINGER);
        List<Song> basicList = songService.getSongList();
        List<Song> filteredList = new ArrayList<>();
        for (Song song : basicList) {
            if (song.getSinger().getName().equals(singer.getName())){
                filteredList.add(song);
            }
        }
        adapter.setSongList(filteredList);
    }
}
