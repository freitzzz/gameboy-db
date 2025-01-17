package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.data.repository.GamesRepository

class GetControversialGames(
    private val gamesRepository: GamesRepository,
) {
    suspend operator fun invoke() = gamesRepository.controversial()
}