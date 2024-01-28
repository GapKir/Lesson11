package com.example.lesson11.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lesson11.BuildConfig
import com.example.lesson11.R
import com.example.lesson11.Utils.Actions
import com.example.lesson11.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var actions: Actions
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.topAppBar.setNavigationOnClickListener { _ -> actions.goToHome() }
    }

    private fun initView() {
        actions = requireActivity() as Actions
        val getPackageName = requireContext().packageName
        val getAppName = requireContext().getString(R.string.app_name)
        val getAppVersion: String = BuildConfig.VERSION_NAME
        val concatinateStringPackageName =
            requireContext().getString(R.string.app_package, getPackageName)
        val concatinateStringAppName = requireContext().getString(R.string.app_names, getAppName)
        val concatinateStringAppVersion =
            requireContext().getString(R.string.app_version, getAppVersion)
        binding.tvTitleAppPackage.text = concatinateStringPackageName
        binding.tvTitleAppName.text = concatinateStringAppName
        binding.tvTitleAppVersion.text = concatinateStringAppVersion
    }
}
