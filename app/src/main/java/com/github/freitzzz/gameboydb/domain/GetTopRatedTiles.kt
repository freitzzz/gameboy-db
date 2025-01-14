package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.data.repository.TilesRepository

class GetTopRatedTiles(
    private val tilesRepository: TilesRepository,
) {
    suspend operator fun invoke() = tilesRepository.top()
}