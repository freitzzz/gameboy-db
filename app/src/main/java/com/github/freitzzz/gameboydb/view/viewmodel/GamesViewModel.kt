package com.github.freitzzz.gameboydb.view.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.freitzzz.gameboydb.core.onIO
import com.github.freitzzz.gameboydb.core.vault
import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.data.model.GamePreview
import com.github.freitzzz.gameboydb.data.model.preview
import com.github.freitzzz.gameboydb.domain.DownloadImage
import com.github.freitzzz.gameboydb.domain.GetControversialGames
import com.github.freitzzz.gameboydb.domain.GetFavoriteGames
import com.github.freitzzz.gameboydb.domain.GetTopRatedGames
import com.github.freitzzz.gameboydb.domain.LoadGame
import com.github.freitzzz.gameboydb.domain.state.GameUpdates
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

// todo: try to to infer query (top rated/controversial/favorites) to prevent view model from becoming a "god viewmodel"
class GamesViewModel : ViewModel() {
    private val getTopRatedGames by lazy { vault().get<GetTopRatedGames>() }
    private val getControversialGames by lazy { vault().get<GetControversialGames>() }
    private val getFavoriteGames by lazy { vault().get<GetFavoriteGames>() }
    private val loadGame by lazy { vault().get<LoadGame>() }
    private val downloadImage by lazy { vault().get<DownloadImage>() }
    private val gameUpdates by lazy { vault().get<GameUpdates>() }

    private val topRatedGames by lazy { MutableLiveData(listOf<GamePreview>()) }
    private val controversialGames by lazy { MutableLiveData(listOf<GamePreview>()) }
    private val favoriteGames: MutableLiveData<List<GamePreview>> by lazy {
        MutableLiveData(
            mutableListOf()
        )
    }

    fun topRated(): LiveData<List<GamePreview>> {
        if (topRatedGames.value?.isNotEmpty() == true) {
            return topRatedGames
        }

        viewModelScope.launch {
            val tiles = getTopRatedGames()
                .unfold { arrayListOf() }
                .map {
                    it.copy(thumbnail = downloadImage(it.thumbnail.toString())
                        .map(Uri::fromFile)
                        .unfold { it.thumbnail }
                    )
                }

            topRatedGames.value = tiles

        }

        return topRatedGames
    }

    fun controversial(): LiveData<List<GamePreview>> {
        if (controversialGames.value?.isNotEmpty() == true) {
            return controversialGames
        }

        viewModelScope.launch {
            val tiles = getControversialGames()
                .unfold { arrayListOf() }
                .map {
                    it.copy(thumbnail = downloadImage(it.thumbnail.toString())
                        .map(Uri::fromFile)
                        .unfold { it.thumbnail }
                    )
                }

            controversialGames.value = tiles
        }

        return controversialGames
    }

    fun favorites(): MutableLiveData<List<GamePreview>> {
        if (favoriteGames.value?.isNotEmpty() == true) {
            return favoriteGames
        }

        viewModelScope.launch {
            val tiles = getFavoriteGames()
                .unfold { arrayListOf() }
                .map {
                    it.copy(thumbnail = downloadImage(it.thumbnail.toString())
                        .map(Uri::fromFile)
                        .unfold { it.thumbnail }
                    )
                }

            favoriteGames.postValue(tiles)
            gameUpdates.onEach {
                val preview = it.preview()
                val games = favoriteGames.value!! as MutableList<GamePreview>

                if (it.favorite) {
                    games.add(preview)
                } else {
                    games.remove(preview)
                }

                favoriteGames.postValue(games)
            }.shareIn(this, SharingStarted.Eagerly)
        }

        return favoriteGames
    }

    fun load(preview: GamePreview, onLoad: (Game) -> Unit) {
        viewModelScope.launch {
            onIO {
                loadGame(preview.id).map {
                    it.copy(thumbnail = downloadImage(it.thumbnail.toString())
                        .map(Uri::fromFile)
                        .unfold { it.thumbnail }
                    )
                }.each(onLoad)
            }
        }
    }
}