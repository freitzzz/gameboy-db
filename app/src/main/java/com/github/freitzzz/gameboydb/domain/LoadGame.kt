package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.core.OperationResult
import com.github.freitzzz.gameboydb.core.Right
import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.data.repository.FavoritesRepository
import com.github.freitzzz.gameboydb.data.repository.GamesRepository

class LoadGame(
    private val gamesRepository: GamesRepository,
    private val favoritesRepository: FavoritesRepository,
) {
    suspend operator fun invoke(id: String): OperationResult<Game> {
        return favoritesRepository.find(id).map {
            if(it.present()) {
                return Right(it.value())
            }

            return gamesRepository.find(id)
        }
    }
}