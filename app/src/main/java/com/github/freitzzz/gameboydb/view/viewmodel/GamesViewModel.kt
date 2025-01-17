package com.github.freitzzz.gameboydb.view.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.freitzzz.gameboydb.core.vault
import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.domain.DownloadImage
import com.github.freitzzz.gameboydb.domain.GetControversialGames
import com.github.freitzzz.gameboydb.domain.GetTopRatedGames
import kotlinx.coroutines.launch

class GamesViewModel : ViewModel() {
    private val getTopRatedGames by lazy { vault().get<GetTopRatedGames>() }
    private val getControversialGames by lazy { vault().get<GetControversialGames>() }
    private val downloadImage by lazy { vault().get<DownloadImage>() }

    private val topRatedGames = MutableLiveData<List<Game>>(arrayListOf())
    private val controversialGames = MutableLiveData<List<Game>>(arrayListOf())

    fun topRated(): LiveData<List<Game>> {
        if (topRatedGames.value?.isNotEmpty() == true) {
            return topRatedGames
        }

        viewModelScope.launch {
            val tiles = getTopRatedGames()
                .unfold { arrayListOf() }
                .map {
                    it.copy(cover = downloadImage(it.cover.toString())
                        .map(Uri::fromFile)
                        .unfold { it.cover }
                    )
                }

            topRatedGames.value = tiles

        }

        return topRatedGames
    }

    fun controversial(): LiveData<List<Game>> {
        if (controversialGames.value?.isNotEmpty() == true) {
            return controversialGames
        }

        viewModelScope.launch {
            val tiles = getControversialGames()
                .unfold { arrayListOf() }
                .map {
                    it.copy(cover = downloadImage(it.cover.toString())
                        .map(Uri::fromFile)
                        .unfold { it.cover }
                    )
                }

            controversialGames.value = tiles
        }

        return controversialGames
    }
}