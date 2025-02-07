package com.github.freitzzz.gameboydb.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.freitzzz.gameboydb.core.vault
import com.github.freitzzz.gameboydb.data.model.GamePreview
import com.github.freitzzz.gameboydb.domain.SearchGames
import com.github.freitzzz.gameboydb.view.adapter.Page
import kotlinx.coroutines.launch

class SearchGamesViewModel : ViewModel() {
    private val searchGames by lazy { vault().get<SearchGames>() }
    val results by lazy { MutableLiveData(Page<GamePreview>()) }

    fun onQuery(query: String) {
        viewModelScope.launch {
            val games = searchGames(query).unfold {
                arrayListOf()
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
            val games = searchGames(query, page).unfold {
                arrayListOf()
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