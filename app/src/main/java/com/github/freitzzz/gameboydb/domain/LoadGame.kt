package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.data.repository.GamesRepository

class LoadGame(
    private val repository: GamesRepository,
) {
    suspend operator fun invoke(id: String) = repository.find(id)
}