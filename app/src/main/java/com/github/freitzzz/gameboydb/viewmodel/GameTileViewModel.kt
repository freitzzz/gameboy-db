package com.github.freitzzz.gameboydb.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.freitzzz.gameboydb.core.vault
import com.github.freitzzz.gameboydb.data.model.GameTile
import com.github.freitzzz.gameboydb.domain.DownloadImage
import com.github.freitzzz.gameboydb.domain.GetTopRatedTiles
import kotlinx.coroutines.launch

class GameTileViewModel : ViewModel() {
    private val getTopRatedTiles by lazy { vault().get<GetTopRatedTiles>() }
    private val downloadImage by lazy { vault().get<DownloadImage>() }
    private val topRatedTiles = MutableLiveData<List<GameTile>>(arrayListOf())

    fun topRated(): LiveData<List<GameTile>> {
        viewModelScope.launch {
            val tiles = getTopRatedTiles().map {
                it.copy(cover = downloadImage(it.cover.toString())
                    .map(Uri::fromFile)
                    .unfold { it.cover }
                )
            }

            topRatedTiles.value = tiles
        }

        return topRatedTiles
    }
}