package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.data.repository.GamesRepository

class UnfavoriteGame(
    private val repository: GamesRepository,
) {
    suspend operator fun invoke(game: Game) = repository.unmarkFavorite(game).map {
        game.copy(favorite = false)
    }
}