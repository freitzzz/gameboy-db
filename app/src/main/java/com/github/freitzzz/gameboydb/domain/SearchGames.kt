package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.data.repository.GamesRepository

class SearchGames(
    private val gamesRepository: GamesRepository,
) {
    suspend operator fun invoke(query: String, page: Int = 1) = gamesRepository.search(query, page)
}