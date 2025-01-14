package com.github.freitzzz.gameboydb.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.fragment.GalleryGameSlideShow
import com.github.freitzzz.gameboydb.viewmodel.GameTileViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}