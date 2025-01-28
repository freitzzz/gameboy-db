package com.github.freitzzz.gameboydb.view.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.freitzzz.gameboydb.core.vault
import com.github.freitzzz.gameboydb.data.model.GamePreview
import com.github.freitzzz.gameboydb.domain.DownloadImage
import com.github.freitzzz.gameboydb.domain.SearchGames
import com.github.freitzzz.gameboydb.view.adapter.Page
import kotlinx.coroutines.launch

class SearchGamesViewModel : ViewModel() {
    private val downloadImage by lazy { vault().get<DownloadImage>() }
    private val searchGames by lazy { vault().get<SearchGames>() }
    val results by lazy { MutableLiveData(Page<GamePreview>()) }

    fun onQuery(query: String) {
        viewModelScope.launch {
            val games = searchGames(query)
                .unfold { arrayListOf() }
                .map {
                    it.copy(thumbnail = downloadImage(it.thumbnail.toString())
                        .map(Uri::fromFile)
                        .unfold { it.thumbnail }
                    )
                }

            results.postValue(
                Page(
                    data = games,
                )
            )
        }
    }

    fun onScrollingToNextPage(query: String) {
        val page = results.value!!.cursor + 1
        viewModelScope.launch {
            val games = searchGames(query, page)
                .unfold { arrayListOf() }
                .map {
                    it.copy(thumbnail = downloadImage(it.thumbnail.toString())
                        .map(Uri::fromFile)
                        .unfold { it.thumbnail }
                    )
                }

            results.postValue(
                Page(
                    data = games,
                    cursor = page
                )
            )
        }
    }
}