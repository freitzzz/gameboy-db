package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.data.repository.FavoritesRepository

class GetFavoriteGames(
    private val repository: FavoritesRepository,
) {
    suspend operator fun invoke() = repository.all()
}