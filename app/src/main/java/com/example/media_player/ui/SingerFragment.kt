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
import com.example.media_player.adapters.SingerListAdapter
import com.example.media_player.databinding.FragmentSingersBinding
import com.example.media_player.model.Singer
import com.example.media_player.model.SingersRepository

class SingerFragment : Fragment() {
    private lateinit var binding: FragmentSingersBinding
    private var singersRepository: SingersRepository? = null
    private lateinit var adapter: SingerListAdapter
    private var actions: Actions? = null

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingersBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.setSingerList(singersRepository!!.getSingers())
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private fun initView() {
        val app = requireActivity().applicationContext as App
        singersRepository = app.singersRepository
        actions = requireActivity() as Actions
        createAdapter()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun createAdapter() {
        adapter = SingerListAdapter()
        if (arguments == null) {
            adapter.setSingerList(singersRepository!!.getSingers())
        } else {
            val singer = requireArguments().getParcelable(
                Constants.KEY_FOR_SEND_SINGER,
                Singer::class.java
            )!!
            val listWithSingleSinger: MutableList<Singer> = ArrayList()
            listWithSingleSinger.add(singer)
            adapter.setSingerList(listWithSingleSinger)
        }
        adapter.setActions(actions)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recycler.layoutManager = layoutManager
        binding.recycler.adapter = adapter
    }

    private fun initListeners() {
        binding.topAppBar.setOnMenuItemClickListener { _ ->
            actions!!.goToSettings()
            true
        }
        binding.topAppBar.setNavigationOnClickListener { _ -> actions!!.goToHome() }
    }
}
