package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.data.repository.FavoritesRepository
import com.github.freitzzz.gameboydb.domain.state.GameUpdates

class UnfavoriteGame(
    private val repository: FavoritesRepository,
    private val state: GameUpdates,
) {
    suspend operator fun invoke(game: Game) = repository.remove(game).map {
        game.copy(favorite = false).also {
            state.tryEmit(it)
        }
    }
}