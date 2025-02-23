package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.data.repository.FavoritesRepository
import com.github.freitzzz.gameboydb.domain.state.GameUpdates

class FavoriteGame(
    private val repository: FavoritesRepository,
    private val state: GameUpdates,
) {
    suspend operator fun invoke(game: Game) = repository.add(game).map {
        game.copy(favorite = true).also {
            state.tryEmit(it)
        }
    }
}