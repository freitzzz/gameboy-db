package com.github.freitzzz.gameboydb.view.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.freitzzz.gameboydb.core.vault
import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.domain.DownloadImage
import com.github.freitzzz.gameboydb.domain.GetControversialTiles
import com.github.freitzzz.gameboydb.domain.GetTopRatedTiles
import kotlinx.coroutines.launch

class GameTileViewModel : ViewModel() {
    private val getTopRatedTiles by lazy { vault().get<GetTopRatedTiles>() }
    private val getControversialTiles by lazy { vault().get<GetControversialTiles>() }
    private val downloadImage by lazy { vault().get<DownloadImage>() }

    private val topRatedTiles = MutableLiveData<List<Game>>(arrayListOf())
    private val controversialTiles = MutableLiveData<List<Game>>(arrayListOf())

    fun topRated(): LiveData<List<Game>> {
        if (topRatedTiles.value?.isNotEmpty() == true) {
            return topRatedTiles
        }

        viewModelScope.launch {
            val tiles = getTopRatedTiles()
                .unfold { arrayListOf() }
                .map {
                    it.copy(cover = downloadImage(it.cover.toString())
                        .map(Uri::fromFile)
                        .unfold { it.cover }
                    )
                }

            topRatedTiles.value = tiles

        }

        return topRatedTiles
    }

    fun controversial(): LiveData<List<Game>> {
        if (controversialTiles.value?.isNotEmpty() == true) {
            return controversialTiles
        }

        viewModelScope.launch {
            val tiles = getControversialTiles()
                .unfold { arrayListOf() }
                .map {
                    it.copy(cover = downloadImage(it.cover.toString())
                        .map(Uri::fromFile)
                        .unfold { it.cover }
                    )
                }

            controversialTiles.value = tiles
        }

        return controversialTiles
    }
}