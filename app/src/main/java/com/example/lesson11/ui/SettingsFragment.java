package com.example.lesson11.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lesson11.BuildConfig;
import com.example.lesson11.R;
import com.example.lesson11.Utils.Actions;
import com.example.lesson11.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    private Actions actions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
    }

    private void initListeners() {
        binding.topAppBar.setNavigationOnClickListener(view -> actions.goToHome());
    }

    private void initView() {
        actions = (Actions) requireActivity();

        String getPackageName = requireContext().getPackageName();
        String getAppName = requireContext().getString(R.string.app_name);
        String getAppVersion = BuildConfig.VERSION_NAME;

        String concatinateStringPackageName = requireContext().getString(R.string.app_package, getPackageName);
        String concatinateStringAppName = requireContext().getString(R.string.app_names, getAppName);
        String concatinateStringAppVersion = requireContext().getString(R.string.app_version, getAppVersion);

        binding.tvTitleAppPackage.setText(concatinateStringPackageName);
        binding.tvTitleAppName.setText(concatinateStringAppName);
        binding.tvTitleAppVersion.setText(concatinateStringAppVersion);
    }
}
