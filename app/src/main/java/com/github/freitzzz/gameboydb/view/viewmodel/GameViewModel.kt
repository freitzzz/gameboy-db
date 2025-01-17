package com.github.freitzzz.gameboydb.view.viewmodel

import androidx.lifecycle.viewModelScope
import com.github.freitzzz.gameboydb.core.vault
import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.domain.FavoriteGame
import com.github.freitzzz.gameboydb.domain.UnfavoriteGame
import kotlinx.coroutines.launch

class GameViewModel(game: Game) : StateViewModel<GameState>(InitialGameState(game)) {
    private val favoriteGame by lazy { vault().get<FavoriteGame>() }
    private val unfavoriteGame by lazy { vault().get<UnfavoriteGame>() }

    fun mark() {
        viewModelScope.launch {
            val result = state.value.let {
                if (it.favorite) unfavoriteGame(it) else favoriteGame(it)
            }

            result.each {
                emit(
                    if (it.favorite) GameMarkedFavorite(it) else GameUnmarkedFavorite(it)
                )
            }
        }
    }
}

sealed class GameState(val value: Game)
class InitialGameState(value: Game) : GameState(value)
class GameMarkedFavorite(value: Game) : GameState(value)
class GameUnmarkedFavorite(value: Game) : GameState(value)
