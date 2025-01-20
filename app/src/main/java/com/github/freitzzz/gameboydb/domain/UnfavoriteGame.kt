package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.data.repository.GamesRepository
import com.github.freitzzz.gameboydb.domain.state.GameUpdates

class UnfavoriteGame(
    private val repository: GamesRepository,
    private val state: GameUpdates,
) {
    suspend operator fun invoke(game: Game) = repository.unmarkFavorite(game).each {
        game.copy(favorite = false).also {
            state.tryEmit(it)
        }
    }
}