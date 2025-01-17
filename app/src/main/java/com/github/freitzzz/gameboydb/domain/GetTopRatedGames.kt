package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.data.repository.GamesRepository

class GetTopRatedGames(
    private val gamesRepository: GamesRepository,
) {
    suspend operator fun invoke() = gamesRepository.top()
}