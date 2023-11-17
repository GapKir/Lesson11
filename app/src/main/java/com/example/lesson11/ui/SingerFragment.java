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
import com.example.lesson11.adapters.SingerListAdapter;
import com.example.lesson11.databinding.FragmentSingersBinding;
import com.example.lesson11.model.Singer;
import com.example.lesson11.model.SingersService;

import java.util.ArrayList;
import java.util.List;

public class SingerFragment extends Fragment {
    private FragmentSingersBinding binding;
    private SingersService singersService;
    private SingerListAdapter adapter;
    private Actions actions;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSingersBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (adapter != null) {
            adapter.setSingerList(singersService.getSingers());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView() {
        App app = (App) requireActivity().getApplicationContext();
        singersService = app.singersService;
        actions = (Actions) requireActivity();

        createAdapter();
    }

    private void createAdapter() {
        adapter = new SingerListAdapter();

        if (getArguments() == null) {
            adapter.setSingerList(singersService.getSingers());
        } else {
            Singer singer = getArguments().getParcelable(Constants.KEY_FOR_SEND_SINGER);
            List<Singer> listWithSingleSinger = new ArrayList<>();
            listWithSingleSinger.add(singer);
            adapter.setSingerList(listWithSingleSinger);
        }
        adapter.setActions(actions);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setAdapter(adapter);
    }


    private void initListeners() {
        binding.topAppBar.setOnMenuItemClickListener(item -> {
            actions.goToSettings();
            return true;
        });
        binding.topAppBar.setNavigationOnClickListener(view -> actions.goToHome());
    }
}
