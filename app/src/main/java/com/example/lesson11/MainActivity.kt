package com.example.lesson11

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lesson11.Utils.Actions
import com.example.lesson11.Utils.Constants
import com.example.lesson11.databinding.ActivityMainBinding
import com.example.lesson11.model.Singer
import com.example.lesson11.model.Song
import com.example.lesson11.services.MusicService
import com.example.lesson11.ui.PlaylistFragment
import com.example.lesson11.ui.SettingsFragment
import com.example.lesson11.ui.SingerFragment

class MainActivity : AppCompatActivity(), Actions, ServiceConnection {
    private lateinit var binding: ActivityMainBinding
    private var musicService: MusicService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListeners()
        initService()
    }

    override fun goToSettings() {
        openFragment(SettingsFragment())
        binding.bottomNavigationView.menu.findItem(R.id.btn_settings).setChecked(true)
    }

    override fun goToHome() {
        openFragment(PlaylistFragment())
        binding.bottomNavigationView.menu.findItem(R.id.btn_playlist).setChecked(true)
    }

    override fun goToSingers() {
        openFragment(SingerFragment())
        binding.bottomNavigationView.menu.findItem(R.id.btn_singer).setChecked(true)
    }

    override fun playSong(song: Song) {
        musicService!!.playSong(song)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun showSingleSinger(singer: Singer) {
        val singerFragment = SingerFragment()
        val bundle = Bundle()
        bundle.putParcelable(Constants.KEY_FOR_SEND_SINGER, singer)
        singerFragment.arguments = bundle
        openFragment(singerFragment)
        binding.bottomNavigationView.menu.findItem(R.id.btn_singer).setChecked(true)
    }

    override fun showFilteredSongsFromSinger(singer: Singer) {
        val playlistFragment = PlaylistFragment()
        val bundle = Bundle()
        bundle.putParcelable(Constants.KEY_FOR_SEND_SINGER, singer)
        playlistFragment.arguments = bundle
        openFragment(playlistFragment)
        binding.bottomNavigationView.menu.findItem(R.id.btn_playlist).setChecked(true)
    }

    override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MyBinder
        musicService = binder.getService()
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        musicService = null
    }


    private fun initView() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragments_container, PlaylistFragment())
            .commit()
    }

    private fun initListeners() {
        binding.bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.btn_settings -> {
                    goToSettings()
                }
                R.id.btn_playlist -> {
                    goToHome()
                }
                else -> {
                    goToSingers()
                }
            }
            true
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(R.id.fragments_container, fragment)
            .commit()
    }

    private fun initService() {
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, this, BIND_AUTO_CREATE)
        startService(intent)
    }

}