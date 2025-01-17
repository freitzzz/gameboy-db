package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.data.repository.GamesRepository

class FavoriteGame(
    private val repository: GamesRepository,
) {
    suspend operator fun invoke(game: Game) = repository.markFavorite(game).map {
        game.copy(favorite = true)
    }
}